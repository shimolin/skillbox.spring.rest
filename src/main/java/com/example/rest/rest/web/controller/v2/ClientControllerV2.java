package com.example.rest.rest.web.controller.v2;

import com.example.rest.rest.mapper.v2.ClientMapperV2;
import com.example.rest.rest.model.Client;
import com.example.rest.rest.model.Order;
import com.example.rest.rest.service.ClientService;
import com.example.rest.rest.web.model.ClientListResponse;
import com.example.rest.rest.web.model.ClientResponse;
import com.example.rest.rest.web.model.CreateClientWithOrderRequest;
import com.example.rest.rest.web.model.UpsertClientRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/client")
@RequiredArgsConstructor
public class ClientControllerV2 {
    private final ClientService databaseClientService;
    private final ClientMapperV2 clientMapper;

    @GetMapping
    public ResponseEntity<ClientListResponse> findAll(){
        return ResponseEntity.ok(
                clientMapper.clientListToClientResponseList(
                        databaseClientService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                clientMapper.clientToResponse(
                        databaseClientService.findById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid UpsertClientRequest request){
        Client newClient = databaseClientService.save(clientMapper.requestToClient(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapper.clientToResponse(newClient));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId, @RequestBody @Valid UpsertClientRequest request){
        Client updatedClient = databaseClientService.update(clientMapper.requestToClient(clientId, request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        databaseClientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/save-with-orders")
    public ResponseEntity<ClientResponse> createWithOrders(@RequestBody CreateClientWithOrderRequest request){
        Client client = Client.builder().name(request.getName()).build();
        List<Order> orders = request.getOrders().stream()
                .map(orderRequest -> Order.builder()
                        .product(orderRequest.getProduct())
                        .cost(orderRequest.getCost())
                        .build())
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                clientMapper.clientToResponse(databaseClientService.saveWithOrders(client,orders))
        );
    }




}
