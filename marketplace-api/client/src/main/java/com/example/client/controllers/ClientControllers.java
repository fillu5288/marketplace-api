package com.example.client.controllers;

import com.example.client.dto.ClientDTO;
import com.example.client.pojo.Client;
import com.example.client.service.ClientService;
import com.example.client.util.ClientErrorResponse;
import com.example.client.util.ClientNotCreated;
import com.example.client.util.ClientNotFound;
import com.example.client.validator.ClientValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientControllers {
    private final ClientService clientService;
    private final ClientValidator clientValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientControllers(ClientService clientService, ClientValidator clientValidator, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<ClientDTO> getOrders() {
        return clientService.findAll().stream()
                .map(this::convertToClientDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDTO getOrder(@PathVariable("id") int id) {
        return convertToClientDTO(clientService.findOneById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        clientService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ClientDTO clientDTO,
                                             BindingResult bindingResult) {
        Client client = convertToClient(clientDTO);
        clientValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";"); // делает красивенькое сообщение об ошибке
            }

            throw new ClientNotCreated(errorMsg.toString());
        }

        clientService.save(convertToClient(clientDTO));
        return ResponseEntity.ok(HttpStatus.OK);        // отправляет ок
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(ClientNotFound clientNotFound) {
        ClientErrorResponse response = new ClientErrorResponse(
                "Orders not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // статус 404, плохо
    }

    private Client convertToClient(ClientDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }

    private ClientDTO convertToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }
}
