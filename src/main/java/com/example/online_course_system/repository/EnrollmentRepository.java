// repository/EnrollmentRepository.java
package com.example.online_course_system.repository;
import com.example.online_course_system.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
  List<Enrollment> findByCourse_Id(Long courseId);
  List<Enrollment> findByStudent_Id(Long studentId);
}
