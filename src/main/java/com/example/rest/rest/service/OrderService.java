package com.example.rest.rest.service;

import com.example.rest.rest.excepton.UpdateStateException;
import com.example.rest.rest.model.Order;
import com.example.rest.rest.web.model.OrderFilter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> filterBy(OrderFilter filter);
    List<Order> findAll();
    Order findById(Long id);

    Order save(Order order);
    Order update(Order order);
    void deleteById(Long id);
    void deleteByIdIn(List<Long> ids);

    default void checkForUpdate(Long orderId) {
        Order currentOrder = findById(orderId);
        Instant now = Instant.now();
        Duration duration = Duration.between(currentOrder.getUpdateAt(), now);
        if (duration.getSeconds() > 5) {
            throw new UpdateStateException("Невозможно обновить заказ");
        }
    }


}
