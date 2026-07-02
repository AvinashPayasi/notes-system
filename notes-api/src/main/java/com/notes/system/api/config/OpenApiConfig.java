package com.notes.system.api.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name="Avinash Payasi",
                        email="payasiavinash1505@gmail.com"
                ),
                description = "REST API for securely managing personal notes using Spring Boot, JWT, PostgreSQL and Docker",
                title = "Notes API",
                version = "1.0"
        )
)
@SecurityScheme(
        name = "JWT Authentication",
        description = "Authentication using a JWT bearer Token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {}