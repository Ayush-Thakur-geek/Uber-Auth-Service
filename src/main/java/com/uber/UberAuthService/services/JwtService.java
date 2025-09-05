package com.uber.UberAuthService.services;

import com.uber.UberEntityService.models.Passenger;
import com.uber.UberAuthService.repositories.PassengerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    private PassengerRepository passengerRepository;

    public JwtService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public String createToken(String email) {

        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);

        System.out.println(passenger);

        if (!passenger.isPresent()) {
            throw new IllegalArgumentException("Invalid email");
        }

        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("email", email);
        payLoad.put("name", passenger.get().getName());
        payLoad.put("phoneNumber", passenger.get().getPhoneNumber());
        payLoad.put("createdAt", passenger.get().getCreatedAt());

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

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Object extractPayload(String token, String payLoadKey) {
        Claims claims = extractAllPayLoads(token);
        return claims.get(payLoadKey);
    }

    public Boolean validateToken(String token, String email) {
        System.out.println("token validation: " + token);
        final String userEmailFetchedFromToken = extractEmail(token);
        System.out.println("userEmailFetchedFromToken: " + userEmailFetchedFromToken);
        System.out.println("correct email: " + userEmailFetchedFromToken.equals(email));
        System.out.println("token expired: " + isTokenExpired(token));
        return userEmailFetchedFromToken.equals(email) && !isTokenExpired(token);
    }

    public String validateTokenForOtherServices(String token) {
        System.out.println("token validation: " + token);
        final String userEmailFetchedFromToken = extractEmail(token);
        if (isTokenExpired(token)) {
            System.out.println("token expired");
            return null;
        }
        return userEmailFetchedFromToken;
    }

    private SecretKey getSECRET() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
