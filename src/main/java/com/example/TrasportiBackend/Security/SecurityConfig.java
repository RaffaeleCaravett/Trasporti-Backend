package com.example.TrasportiBackend.Security;

import com.example.TrasportiBackend.exceptions.ExceptionsHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    JWTAuthFilter jwtAuthFilter;
    @Autowired
    ExceptionsHandlerFilter exceptionsHandlerFilter;
    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
}
