package com.notes.system.api.service;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.ApiStatus;
import com.notes.system.api.UsersDetails;
import com.notes.system.api.dto.LogInRequest;
import com.notes.system.api.dto.RegistrationRequestDTO;
import com.notes.system.api.entity.Users;
import com.notes.system.api.exception.InvalidCredentialsException;
import com.notes.system.api.exception.UserAlreadyExistsExcpetion;
import com.notes.system.api.repo.UsersRepo;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UsersRepo usersRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService){
        this.usersRepo=usersRepo;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    public ApiResponse<Object> registerUser(RegistrationRequestDTO registrationRequestDTO) {
        String tempEmail=registrationRequestDTO.getEmail();
        String tempPassword=registrationRequestDTO.getPassword();

        if(tempPassword.equals(tempEmail)){
            throw new InvalidCredentialsException("Password cannot be the same as your email address.");
        }
        if(checkUser(tempEmail)){
            throw new UserAlreadyExistsExcpetion("This email is already registered. Try logging in instead");
        }else{
            String password=passwordEncoder.encode(tempPassword);
            String email=tempEmail.trim().toLowerCase();
            usersRepo.save(new Users(email, password));
        }

        return new ApiResponse<>(ApiStatus.SUCCESS,"User registered successfully", null);
    }

    private boolean checkUser(String email){
        return usersRepo.existsByEmail(email);
    }

    public ApiResponse<Object> loginUser(@Valid LogInRequest logInRequest) {
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                logInRequest.getEmail(),
                logInRequest.getPassword()
        ));

        var token = jwtService.generateToken((UsersDetails) authentication.getPrincipal());

        return new ApiResponse<>(ApiStatus.SUCCESS, "User logged in successfully", token);
    }
}
