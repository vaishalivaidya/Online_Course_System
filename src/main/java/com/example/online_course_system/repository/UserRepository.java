// repository/UserRepository.java
package com.example.online_course_system.repository;
import com.example.online_course_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
