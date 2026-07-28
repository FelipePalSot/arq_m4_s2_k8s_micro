package com.tecsup.app.micro.product.infrastructure.config;

/*
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
*/

/**
 * Configuración de Spring Security para product-service
 *
 * Paquete: com.tecsup.app.micro.product.infrastructure.config
 * Sesión 1: Autorización por URL
 * Sesión 2: Validación de JWT (product-service NO genera tokens, solo los valida)
 *
 * Endpoints:
 *   GET  /api/products             → público
 *   GET  /api/products/available   → público
 *   GET  /api/products/{id}        → público
 *   GET  /api/products/user/{userId} → autenticado
 *   POST /api/products             → ADMIN
 *   PUT  /api/products/{id}        → ADMIN
 *   DELETE /api/products/{id}      → ADMIN
 *   POST /api/orders               → autenticado (Sesión 3)
 *   GET  /api/products/health      → público
 *   Actuator /actuator/health      → público
 */
/*
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
*/
