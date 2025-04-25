package com.example.test.tp1.client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientModel,Long> {
   Optional<ClientModel> findByTel(String nom);
}
