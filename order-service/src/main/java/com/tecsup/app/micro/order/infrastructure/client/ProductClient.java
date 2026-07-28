package com.tecsup.app.micro.order.infrastructure.client;

import com.tecsup.app.micro.order.domain.exception.ProductNotFoundException;
import com.tecsup.app.micro.order.domain.exception.ProductServiceException;
import com.tecsup.app.micro.order.infrastructure.client.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductDTO getProductById(Long productId) {
        log.info("Calling Product Service to get product with id: {}", productId);
        String url = productServiceUrl + "/api/products/" + productId;
        try {
            ProductDTO product = restTemplate.getForObject(url, ProductDTO.class);
            log.info("Product retrieved successfully: {} - price: {}", product.getName(), product.getPrice());
            return product;
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Product not found with id: {}", productId);
            throw new ProductNotFoundException(productId);
        } catch (Exception e) {
            log.error("Error calling Product Service for productId {}: {}", productId, e.getMessage());
            throw new ProductServiceException("Error calling Product Service: " + e.getMessage(), e);
        }
    }
}

