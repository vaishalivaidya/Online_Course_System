// controller/StudentController.java
package com.example.online_course_system.controller;
import com.example.online_course_system.dto.ProgressUpdateRequest;
import com.example.online_course_system.entity.Enrollment;
import com.example.online_course_system.entity.User;
import com.example.online_course_system.service.CourseService;
import com.example.online_course_system.service.EnrollmentService;
import com.example.online_course_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/student")
public class StudentController {
  private final CourseService courses; private final EnrollmentService enrollments; private final UserService users;
  public StudentController(CourseService courses, EnrollmentService enrollments, UserService users) {
    this.courses = courses; this.enrollments = enrollments; this.users = users;
  }
  @GetMapping("/courses") public Object listCourses() { return courses.listApproved(); }
  @PostMapping("/enroll/{courseId}")
  public Enrollment enroll(@PathVariable Long courseId, Authentication auth) {
    User student = users.findByUsername(auth.getName()).orElseThrow();
    return enrollments.enroll(courseId, student.getId());
  }
  @PutMapping("/enrollment/{id}/progress")
  public Enrollment updateProgress(@PathVariable Long id, @Valid @RequestBody ProgressUpdateRequest req) {
    return enrollments.updateProgress(id, req.getProgress());
  }
  @GetMapping("/enrollments/me")
  public List<Enrollment> myEnrollments(Authentication auth) {
    User student = users.findByUsername(auth.getName()).orElseThrow();
    return enrollments.byStudent(student.getId());
  }
}
