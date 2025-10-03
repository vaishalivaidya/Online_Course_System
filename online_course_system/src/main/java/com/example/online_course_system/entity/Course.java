// entity/Course.java
package com.example.online_course_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Course {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false, length=120) private String title;
  @Column(nullable=false, length=2000) private String description;
  private int duration; // hours
  private double fee;
  @Column(length=64) private String category;

  @Enumerated(EnumType.STRING) @Column(nullable=false, length=20)
  private CourseStatus status = CourseStatus.PENDING;

  @ManyToOne(optional=false) private User instructor;
}
