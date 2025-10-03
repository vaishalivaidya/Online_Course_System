// entity/Enrollment.java
package com.example.online_course_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Enrollment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(optional=false) private User student;
  @ManyToOne(optional=false) private Course course;
  private double progress = 0.0;
  @Enumerated(EnumType.STRING) @Column(nullable=false, length=20)
  private EnrollmentStatus status = EnrollmentStatus.NOT_STARTED;
}
