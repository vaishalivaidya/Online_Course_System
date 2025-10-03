// config/JwtUtil.java
package com.example.online_course_system.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key; import java.util.Date;

@Component
public class JwtUtil {
  private final Key key; private final long expMs;
  public JwtUtil(@Value("${app.jwt.secret}") String secret,
                 @Value("${app.jwt.expiration}") long expMs) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes()); this.expMs = expMs;
  }
  public String generate(String username, String role) {
    Date now = new Date(); Date exp = new Date(now.getTime() + expMs);
    return Jwts.builder().setSubject(username).claim("role", role)
      .setIssuedAt(now).setExpiration(exp).signWith(key).compact();
  }
  public Claims parse(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build()
      .parseClaimsJws(token).getBody();
  }
}
