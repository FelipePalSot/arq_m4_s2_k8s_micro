package com.tecsup.app.micro.order.infrastructure.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull(message = "userId is required")
        Long userId,

        @NotEmpty(message = "items cannot be empty")
        @Valid
        List<OrderItemRequest> items
) {}

