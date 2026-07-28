package com.tecsup.app.micro.order.infrastructure.persistence.mapper;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.domain.model.OrderStatus;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceMapper {

    public Order toDomain(OrderEntity entity) {
        if (entity == null) return null;
        List<OrderItem> items = entity.getItems().stream()
                .map(this::itemToDomain)
                .collect(Collectors.toList());
        return Order.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .userId(entity.getUserId())
                .status(OrderStatus.valueOf(entity.getStatus()))
                .totalAmount(entity.getTotalAmount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .items(items)
                .build();
    }

    public OrderEntity toEntity(Order domain) {
        if (domain == null) return null;
        OrderEntity entity = OrderEntity.builder()
                .id(domain.getId())
                .orderNumber(domain.getOrderNumber())
                .userId(domain.getUserId())
                .status(domain.getStatus().name())
                .totalAmount(domain.getTotalAmount())
                .build();
        List<OrderItemEntity> items = domain.getItems().stream()
                .map(item -> itemToEntity(item, entity))
                .collect(Collectors.toList());
        entity.setItems(items);
        return entity;
    }

    private OrderItem itemToDomain(OrderItemEntity entity) {
        return OrderItem.builder()
                .id(entity.getId())
                .orderId(entity.getOrder() != null ? entity.getOrder().getId() : null)
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .subtotal(entity.getSubtotal())
                .build();
    }

    private OrderItemEntity itemToEntity(OrderItem domain, OrderEntity orderEntity) {
        return OrderItemEntity.builder()
                .id(domain.getId())
                .order(orderEntity)
                .productId(domain.getProductId())
                .quantity(domain.getQuantity())
                .unitPrice(domain.getUnitPrice())
                .subtotal(domain.getSubtotal())
                .build();
    }
}

