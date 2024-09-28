package com.example.client.repo;

import com.example.client.pojo.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ClientRepo extends JpaRepository<Client, Integer> {
    Optional<Client> findById(int id);
    Optional<Client> deleteAllById(int id);
}
