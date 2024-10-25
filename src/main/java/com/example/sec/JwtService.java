package com.example.sec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String base64Key;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        SecretKey secretKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(base64Key));
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        SecretKey secretKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(base64Key));
        final String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    private String getUsernameFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(base64Key));
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    private Boolean isTokenExpired(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(base64Key));
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(base64Key));
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration();
    }
}