// controller/AuthController.java
package com.example.online_course_system.controller;

import com.example.online_course_system.config.JwtUtil;
import com.example.online_course_system.dto.AuthRequest;
import com.example.online_course_system.dto.AuthResponse;
import com.example.online_course_system.entity.Role;
import com.example.online_course_system.entity.User;
import com.example.online_course_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/auth")
public class AuthController {
  private final UserService users; private final AuthenticationManager authManager; private final JwtUtil jwt;
  public AuthController(UserService users, AuthenticationManager authManager, JwtUtil jwt) {
    this.users = users; this.authManager = authManager; this.jwt = jwt;
  }
  @PostMapping("/register")
  public User register(@Valid @RequestBody AuthRequest req) {
    return users.register(req.getUsername(), req.getPassword(), Role.valueOf(req.getRole()));
  }
  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody AuthRequest req) {
    Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    UserDetails principal = (UserDetails) auth.getPrincipal();
    String role = principal.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
    return new AuthResponse(jwt.generate(principal.getUsername(), role));
  }
}
