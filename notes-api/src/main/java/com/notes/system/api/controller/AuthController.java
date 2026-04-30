package com.notes.system.api.controller;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.dto.LogInRequest;
import com.notes.system.api.dto.RegistrationRequestDTO;
import com.notes.system.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notes")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) {
        ApiResponse<Object> response = authService.registerUser(registrationRequestDTO);

        //Status Code: 201
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LogInRequest logInRequest){
        ApiResponse<Object> response=authService.loginUser(logInRequest);

        //Status Code: 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
