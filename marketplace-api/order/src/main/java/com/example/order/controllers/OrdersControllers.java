package com.example.order.controllers;

import com.example.order.dto.OrdersDTO;
import com.example.order.pojo.Orders;
import com.example.order.repo.OrdersRepo;
import com.example.order.service.OrdersService;
import com.example.order.util.OrdersErrorResponse;
import com.example.order.util.OrdersNotCreated;
import com.example.order.util.OrdersNotFound;
import com.example.order.validator.OrdersValidator;
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
public class OrdersControllers {
    private final OrdersService ordersService;
    private final OrdersValidator ordersValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public OrdersControllers(OrdersService ordersService, OrdersValidator ordersValidator, ModelMapper modelMapper) {
        this.ordersService = ordersService;
        this.ordersValidator = ordersValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<OrdersDTO> getOrders() {
        return ordersService.findAll().stream()
                .map(this::convertToOrdersDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrdersDTO getOrder(@PathVariable("id") int id) {
        return convertToOrdersDTO(ordersService.findOneById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        ordersService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid OrdersDTO ordersDTO,
                                             BindingResult bindingResult) {
        Orders orders = convertToOrders(ordersDTO);
        ordersValidator.validate(orders, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";"); // делает красивенькое сообщение об ошибке
            }

            throw new OrdersNotCreated(errorMsg.toString());
        }

        ordersService.save(convertToOrders(ordersDTO));
        return ResponseEntity.ok(HttpStatus.OK);        // отправляет ок
    }

    @ExceptionHandler
    private ResponseEntity<OrdersErrorResponse> handleException(OrdersNotFound ordersNotFound) {
        OrdersErrorResponse response = new OrdersErrorResponse(
                "Orders not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // статус 404, плохо
    }

    private Orders convertToOrders(OrdersDTO ordersDTO) {
        return modelMapper.map(ordersDTO, Orders.class);
    }

    private OrdersDTO convertToOrdersDTO(Orders orders) {
        return modelMapper.map(orders, OrdersDTO.class);
    }
}
