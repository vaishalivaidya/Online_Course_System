// entity/User.java
package com.example.online_course_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true, nullable=false, length=64) private String username;
  @Column(nullable=false) private String password;
  @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private Role role;
}
