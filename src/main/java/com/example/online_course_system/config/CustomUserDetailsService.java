// config/CustomUserDetailsService.java
package com.example.online_course_system.config;

import com.example.online_course_system.entity.User;
import com.example.online_course_system.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository users;
  public CustomUserDetailsService(UserRepository users) { this.users = users; }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = users.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    String role = "ROLE_" + u.getRole().name();
    return new org.springframework.security.core.userdetails.User(
      u.getUsername(), u.getPassword(), List.of(new SimpleGrantedAuthority(role)));
  }
}
