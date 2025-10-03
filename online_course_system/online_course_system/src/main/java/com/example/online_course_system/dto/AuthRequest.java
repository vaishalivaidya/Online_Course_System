// dto/AuthRequest.java
package com.example.online_course_system.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter; import lombok.Setter;
@Getter @Setter
public class AuthRequest {
  @NotBlank private String username;
  @NotBlank private String password;
  @NotBlank private String role; // ADMIN, INSTRUCTOR, STUDENT
}
