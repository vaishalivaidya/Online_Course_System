// repository/PaymentRepository.java
package com.example.online_course_system.repository;
import com.example.online_course_system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentRepository extends JpaRepository<Payment, Long> {}
