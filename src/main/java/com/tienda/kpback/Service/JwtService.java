package com.tienda.kpback.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.tienda.kpback.Entity.UsuarioEnt;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${JWT_SECRET}")
    private String secretKeyString;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String username, Long userId, UsuarioEnt.Rol rol) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("rol", rol.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String username, Long userId, String nombre, String direccion, UsuarioEnt.Rol rol) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("nombre", nombre)
                .claim("direccion", direccion)
                .claim("rol", rol.name())  
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public Long extractUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
            return claims.get("userId", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String extractRol(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
            return claims.get("rol", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String extractNombre(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
            return claims.get("nombre", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String extractDireccion(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
            return claims.get("direccion", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}