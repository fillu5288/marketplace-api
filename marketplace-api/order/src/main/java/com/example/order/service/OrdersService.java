package com.example.order.service;

import com.example.order.dto.OrdersDTO;
import com.example.order.pojo.Orders;
import com.example.order.repo.OrdersRepo;
import com.example.order.util.OrdersNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrdersService {
    private final OrdersRepo ordersRepo;

    @Autowired
    public OrdersService(OrdersRepo ordersRepo) {
        this.ordersRepo = ordersRepo;
    }

    public List<Orders> findAll() {return ordersRepo.findAll();}

    public Optional<Orders> findOneId(int id) {return ordersRepo.findById(id);}

    public Orders findOneById(int id) {
        Optional<Orders> foundOrders = ordersRepo.findById(id);

        return foundOrders.orElseThrow(OrdersNotFound::new);
    }

    @Transactional
    public void save(Orders orders) {
        ServerDataOrders(orders);

        ordersRepo.save(orders);
    }

    @Transactional
    public void delete(int id) {ordersRepo.deleteAllById(id);}

    private void ServerDataOrders(Orders orders) {
        orders.setCreatedAt(LocalDateTime.now());
    }
}
