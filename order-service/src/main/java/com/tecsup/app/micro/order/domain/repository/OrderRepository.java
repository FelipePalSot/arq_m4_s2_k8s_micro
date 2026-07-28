package com.tecsup.app.micro.order.domain.repository;

import com.tecsup.app.micro.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio de Orden (Clean Architecture)
 */
public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    List<Order> findByUserId(Long userId);
    Order save(Order order);
    void deleteById(Long id);
    boolean existsByOrderNumber(String orderNumber);
}

