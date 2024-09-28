package com.example.order.repo;

import com.example.order.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    Optional<Orders> findById(int id);
    Optional<Orders> deleteAllById(int id);
}
