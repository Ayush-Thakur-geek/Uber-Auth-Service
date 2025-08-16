package com.uber.UberAuthService.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    private String createToken(Map<String, Object> payLoad, String email) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);

        return Jwts.builder()
                .claims(payLoad)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                .signWith(getSECRET())
                .compact();
    }

    private Claims extractAllPayLoads(String token) {
        return Jwts
                .parser()
                .verifyWith(getSECRET())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllPayLoads(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Object extractPayload(String token, String payLoadKey) {
        Claims claims = extractAllPayLoads(token);
        return claims.get(payLoadKey);
    }

    private Boolean validateToken(String token, String email) {
        final String userEmailFetchedFromToken = extractEmail(token);
        return userEmailFetchedFromToken.equals(email) && isTokenExpired(token);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("email", "aer@uber.com");
        payLoad.put("name", "Uber Auth Service");
        payLoad.put("role", "admin");
        payLoad.put("password", "password");
        String token = createToken(payLoad, "admin");
        System.out.println(extractAllPayLoads(token));
        System.out.println(extractPayload(token, "email").toString());
    }

    private SecretKey getSECRET() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
