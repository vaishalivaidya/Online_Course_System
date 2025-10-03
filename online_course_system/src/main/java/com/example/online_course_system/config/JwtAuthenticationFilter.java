// config/JwtAuthenticationFilter.java
package com.example.online_course_system.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwt; private final CustomUserDetailsService uds;
  public JwtAuthenticationFilter(JwtUtil jwt, CustomUserDetailsService uds) {
    this.jwt = jwt; this.uds = uds;
  }
  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {
    String header = req.getHeader("Authorization");
    String token = (StringUtils.hasText(header) && header.startsWith("Bearer ")) ? header.substring(7) : null;
    if (token != null) {
      try {
        Claims claims = jwt.parse(token);
        String username = claims.getSubject();
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          var userDetails = uds.loadUserByUsername(username);
          var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      } catch (Exception ignored) {}
    }
    chain.doFilter(req, res);
  }
}
