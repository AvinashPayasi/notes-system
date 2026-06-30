package com.notes.system.api.security;

import com.notes.system.api.UsersDetails;
import com.notes.system.api.repository.UsersRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {
    private final UsersRepo usersRepo;

    public SecurityConfig(UsersRepo usersRepo){
        this.usersRepo=usersRepo;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService(){
        return  email -> usersRepo.findByEmail(email)
                .map(UsersDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid username or password"));
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        Map<String, PasswordEncoder> encoders=new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());

        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthenticationFilter){
         httpSecurity.authorizeHttpRequests(
                 customizer -> customizer.requestMatchers("/api/v1/notes/register").permitAll()
                         .requestMatchers("/api/v1/notes/login").permitAll()
                         .anyRequest().authenticated());

         httpSecurity.csrf(csrf -> csrf.disable());

         httpSecurity.sessionManagement(
                 session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 );

         httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

         return httpSecurity.build();
    }
}
