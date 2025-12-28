package com.v1.manfaa.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret.current}")
    private String CURRENT_SECRET;

    @Value("${jwt.secret.previous:#{null}}")
    private String PREVIOUS_SECRET;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;

    private SecretKey currentKey;
    private SecretKey previousKey;

    @PostConstruct
    public void init() {
        // Decode Base64 secret and create SecretKey
        byte[] currentKeyBytes = Base64.getDecoder().decode(CURRENT_SECRET);
        this.currentKey = Keys.hmacShaKeyFor(currentKeyBytes);

        if (PREVIOUS_SECRET != null && !PREVIOUS_SECRET.isEmpty()) {
            byte[] previousKeyBytes = Base64.getDecoder().decode(PREVIOUS_SECRET);
            this.previousKey = Keys.hmacShaKeyFor(previousKeyBytes);
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // Try current key first
        try {
            return Jwts.parser()
                    .verifyWith(currentKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            // If current key fails, try previous key (during rotation)
            if (previousKey != null) {
                try {
                    return Jwts.parser()
                            .verifyWith(previousKey)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();
                } catch (Exception ex) {
                    throw new RuntimeException("Invalid token", ex);
                }
            }
            throw new RuntimeException("Invalid token", e);
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String generateToken(Map<String, Object> extraClaims, String username) {
        return createToken(extraClaims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(currentKey, Jwts.SIG.HS256)
                .compact();
    }
}