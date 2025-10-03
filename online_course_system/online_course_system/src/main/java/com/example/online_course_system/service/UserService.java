// service/UserService.java
package com.example.online_course_system.service;
import com.example.online_course_system.entity.Role;
import com.example.online_course_system.entity.User;
import com.example.online_course_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository users; private final PasswordEncoder encoder;
  public UserService(UserRepository users, PasswordEncoder encoder) { this.users = users; this.encoder = encoder; }
  public User register(String username, String rawPassword, Role role) {
    User u = new User(); u.setUsername(username); u.setPassword(encoder.encode(rawPassword)); u.setRole(role);
    return users.save(u);
  }
  public Optional<User> findByUsername(String username) { return users.findByUsername(username); }
}
