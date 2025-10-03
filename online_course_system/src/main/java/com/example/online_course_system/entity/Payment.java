// entity/Payment.java
package com.example.online_course_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Payment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true, nullable=false, length=64) private String orderId;
  @Column(unique=true, nullable=false, length=64) private String transactionId;
  @Column(nullable=false, length=20) private String status; // SUCCESS or FAILED
  @OneToOne(optional=false) private Enrollment enrollment;
}
