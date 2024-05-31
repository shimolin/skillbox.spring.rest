package com.example.rest.rest.repository;

import com.example.rest.rest.model.Client;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatabaseClientRepository extends JpaRepository<Client, Long> {

    @Override
    @EntityGraph(attributePaths = {"orders"})
    List<Client> findAll();
}
