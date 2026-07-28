package com.tecsup.app.micro.order.infrastructure.web.controller;

import com.tecsup.app.micro.order.application.service.OrderApplicationService;
import com.tecsup.app.micro.order.application.usecase.CreateOrderUseCase;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.infrastructure.web.dto.CreateOrderRequest;
import com.tecsup.app.micro.order.infrastructure.web.dto.OrderResponse;
import com.tecsup.app.micro.order.infrastructure.web.mapper.OrderDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST de Órdenes
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderDtoMapper orderDtoMapper;

    /**
     * Crea una nueva orden de compra
     * POST /api/orders
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("REST request to create order for userId: {}", request.userId());
        List<CreateOrderUseCase.OrderItemRequest> itemRequests = request.items().stream()
                .map(i -> new CreateOrderUseCase.OrderItemRequest(i.productId(), i.quantity()))
                .toList();
        Order order = orderApplicationService.createOrder(request.userId(), itemRequests);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDtoMapper.toResponse(order));
    }

    /**
     * Obtiene todas las órdenes
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("REST request to get all orders");
        List<Order> orders = orderApplicationService.getAllOrders();
        return ResponseEntity.ok(orderDtoMapper.toResponseList(orders));
    }

    /**
     * Obtiene una orden por ID
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        log.info("REST request to get order by id: {}", id);
        Order order = orderApplicationService.getOrderById(id);
        return ResponseEntity.ok(orderDtoMapper.toResponse(order));
    }

    /**
     * Obtiene las órdenes de un usuario
     * GET /api/orders/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        log.info("REST request to get orders by userId: {}", userId);
        List<Order> orders = orderApplicationService.getOrdersByUser(userId);
        return ResponseEntity.ok(orderDtoMapper.toResponseList(orders));
    }

    /**
     * Endpoint de salud
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Order Service running with Clean Architecture!");
    }
}

