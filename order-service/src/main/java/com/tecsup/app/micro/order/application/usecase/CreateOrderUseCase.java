package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.domain.model.OrderStatus;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.infrastructure.client.ProductClient;
import com.tecsup.app.micro.order.infrastructure.client.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private static final AtomicLong counter = new AtomicLong(1);

    public Order execute(Long userId, List<OrderItemRequest> itemRequests) {
        log.info("Creating order for userId: {} with {} items", userId, itemRequests.size());

        // 1. Construir items validando con Product Service
        List<OrderItem> orderItems = itemRequests.stream().map(req -> {
            ProductDTO product = productClient.getProductById(req.productId());
            OrderItem item = OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(req.quantity())
                    .unitPrice(product.getPrice())
                    .build();
            item.calculateSubtotal();
            return item;
        }).collect(Collectors.toList());

        // 2. Construir la orden
        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .userId(userId)
                .status(OrderStatus.PENDING)
                .items(orderItems)
                .build();

        // 3. Calcular total
        order.calculateTotal();

        // 4. Guardar
        Order saved = orderRepository.save(order);
        log.info("Order created successfully: {} - total: {}", saved.getOrderNumber(), saved.getTotalAmount());
        return saved;
    }

    private String generateOrderNumber() {
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String seq = String.format("%03d", counter.getAndIncrement());
        String candidate = "ORD-" + year + "-" + seq;
        // Asegurar unicidad
        while (orderRepository.existsByOrderNumber(candidate)) {
            seq = String.format("%03d", counter.getAndIncrement());
            candidate = "ORD-" + year + "-" + seq;
        }
        return candidate;
    }

    public record OrderItemRequest(Long productId, Integer quantity) {}
}

