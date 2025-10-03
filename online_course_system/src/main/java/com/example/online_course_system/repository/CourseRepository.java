// repository/CourseRepository.java
package com.example.online_course_system.repository;
import com.example.online_course_system.entity.Course;
import com.example.online_course_system.entity.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CourseRepository extends JpaRepository<Course, Long> {
  List<Course> findByStatus(CourseStatus status);
  List<Course> findByInstructor_Id(Long instructorId);
}
