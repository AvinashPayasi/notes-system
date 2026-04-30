package com.notes.system.api.service;

import com.notes.system.api.UsersDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecretKey;
    @Value(("${jwt.expiration}"))
    private Long jwtExpirationTime;

    public Claims validateAndGetClaims(String token){
        try {
            Claims claims = parseClaims(token);
            if (isTokenNonExpired(claims)) {
                return claims;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String generateToken(UsersDetails usersDetails){
        return Jwts.builder()
                .subject(usersDetails.getUserId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtExpirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    private boolean isTokenNonExpired(Claims claims){
        return claims.getExpiration().after(new Date());
    }

    private Claims parseClaims(String token){
         return Jwts.parser()
                 .verifyWith(getSigningKey())
                 .build()
                 .parseSignedClaims(token)
                 .getPayload();
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecretKey));
    }
}
