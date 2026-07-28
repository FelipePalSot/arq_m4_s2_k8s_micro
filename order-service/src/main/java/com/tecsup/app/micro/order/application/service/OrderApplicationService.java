package com.tecsup.app.micro.order.application.service;

import com.tecsup.app.micro.order.application.usecase.*;
import com.tecsup.app.micro.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de Aplicación de Órdenes
 * Orquesta los casos de uso y maneja las transacciones
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderApplicationService {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final GetOrdersByUserUseCase getOrdersByUserUseCase;

    @Transactional
    public Order createOrder(Long userId, List<CreateOrderUseCase.OrderItemRequest> items) {
        return createOrderUseCase.execute(userId, items);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return getAllOrdersUseCase.execute();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return getOrderByIdUseCase.execute(id);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {
        return getOrdersByUserUseCase.execute(userId);
    }
}

