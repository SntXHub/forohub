//Proyecto ForoHub Alura ONE
package com.forohub.forohub.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    private final SecretKey secretKey;
    private final Long jwtExpiration;

    public TokenService(@Value("${jwt.secret}") String jwtSecret,
                        @Value("${jwt.expiration:3600000}") Long jwtExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret)); // Decodifica la clave Base64
        this.jwtExpiration = jwtExpiration;
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            System.out.println("Error al validar el token: " + e.getMessage());
            return null;
        }
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Usa la clave secreta para firmar el token
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // Extrae el cuerpo de los claims
        } catch (Exception e) {
            System.out.println("Error al obtener los claims del token: " + e.getMessage());
            return null; // Si hay un error, retorna null
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true; // Si hay un error, asumimos que el token está expirado
        }
    }
}
