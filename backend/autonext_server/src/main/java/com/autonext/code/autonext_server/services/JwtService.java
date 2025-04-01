package com.autonext.code.autonext_server.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.autonext.code.autonext_server.exceptions.InvalidJwtException;
import com.autonext.code.autonext_server.models.User;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secretKey;

  public String generateToken(User user) {
    return Jwts.builder()
        .subject(user.getEmail())
        .claim("id", user.getId())
        .claim("role", user.getRole())
        .claim("name", user.getName())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        // Firmado de la clave secreta
        .signWith(getSigningKey())
        .compact();
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    try {
      final Claims claims = Jwts.parser()
      // Verifica el token con la clave
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();

  return claimsResolver.apply(claims);
    } catch (JwtException e) {
      throw new InvalidJwtException("Token inválido o expirado");
    }

  }

  public int extractUserId(String token) {
    return extractClaim(token, claims -> claims.get("id", Integer.class));
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
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
