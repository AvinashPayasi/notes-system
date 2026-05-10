package com.notes.system.api.security;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.ApiStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtService jwtService, ObjectMapper objectMapper){
        this.jwtService = jwtService;
        this.objectMapper=objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        final String authHeader= request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken= authHeader.substring(7);

        try {
            final Claims claims = jwtService.validateAndGetClaims(jwtToken);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UUID userId = UUID.fromString(claims.getSubject());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException expiredJwtException){
            ApiResponse<Object> apiResponse=new ApiResponse(ApiStatus.ERROR, "Token expired");
            writeResponse(response, apiResponse);
        }catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException exception){
            ApiResponse<Object> apiResponse=new ApiResponse(ApiStatus.ERROR, "Invalid token");
            writeResponse(response, apiResponse);
        }
    }

    private void writeResponse(HttpServletResponse response, ApiResponse<Object> apiResponse) throws IOException{
        String json= objectMapper.writeValueAsString(apiResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(json);
        response.getWriter().flush();
    }
}

