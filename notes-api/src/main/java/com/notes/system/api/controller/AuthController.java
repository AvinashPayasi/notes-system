package com.notes.system.api.controller;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.dto.LogInRequest;
import com.notes.system.api.dto.RegistrationRequest;
import com.notes.system.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notes")
@Tag(name = "Authentication", description = "Endpoints for user registration and authentication")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Register a new user",
        description = "Creates a new user account using the provided email and password.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "201",
                        description = "User registered successfully",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Validation failed or invalid request body",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "409",
                        description = "This email is already registered",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Something went wrong",
                        content = @Content
                )
        }
    )
    @PostMapping("register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegistrationRequest registrationRequestDTO) {
        ApiResponse<Object> response = authService.registerUser(registrationRequestDTO);

        //Status Code: 201
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Login user",
        description = "Authenticates a user using email and password and returns a JWT token.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "User logged in successfully",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Validation failed or invalid request body",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "401",
                        description = "Invalid username or password",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Something went wrong",
                        content = @Content
                )
        }
    )
    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LogInRequest logInRequest){
        ApiResponse<Object> response=authService.loginUser(logInRequest);

        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
