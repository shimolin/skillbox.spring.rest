package com.example.rest.rest.service;

import com.example.rest.rest.model.Client;
import com.example.rest.rest.model.Order;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAll();
    Client findById(Long id);
    Client save(Client client);
    Client update(Client client);

    void deleteById(Long id);

    Client saveWithOrders(Client client, List<Order> orders);

}
