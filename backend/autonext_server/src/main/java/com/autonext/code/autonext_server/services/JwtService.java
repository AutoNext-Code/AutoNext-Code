package com.autonext.code.autonext_server.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.models.User;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {

  private static final String SECRET_KEY = "supersecretykeyforsigningjwt123456789012345";

  public String generateToken(User user) {
    return Jwts.builder()
        .subject(user.getName())
        .claim("role", user.getRole())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(getSigningKey())
        .compact();
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return claimsResolver.apply(claims);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    Date expirationDate = extractClaim(token, Claims::getExpiration);
    return expirationDate.before(new Date());
  }
}
