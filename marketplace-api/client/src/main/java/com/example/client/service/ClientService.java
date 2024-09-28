package com.example.client.service;

import com.example.client.pojo.Client;
import com.example.client.repo.ClientRepo;
import com.example.client.util.ClientNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientService {
    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public List<Client> findAll() {return clientRepo.findAll();}

    public Optional<Client> findOneId(int id) {return clientRepo.findById(id);}

    public Client findOneById(int id) {
        Optional<Client> foundOrders = clientRepo.findById(id);

        return foundOrders.orElseThrow(ClientNotFound::new);
    }

    @Transactional
    public void save(Client client) {
        ServerDataOrders(client);

        clientRepo.save(client);
    }

    @Transactional
    public void delete(int id) {clientRepo.deleteAllById(id);}

    private void ServerDataOrders(Client client) {
        client.setCreatedAt(LocalDateTime.now());
    }
}
