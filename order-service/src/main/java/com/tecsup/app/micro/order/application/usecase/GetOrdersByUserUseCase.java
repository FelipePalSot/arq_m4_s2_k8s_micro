package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetOrdersByUserUseCase {
    private final OrderRepository orderRepository;

    public List<Order> execute(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}

