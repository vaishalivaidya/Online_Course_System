// service/EnrollmentService.java
package com.example.online_course_system.service;
import com.example.online_course_system.entity.*;
import com.example.online_course_system.repository.*;
import org.springframework.stereotype.Service;
import java.util.List; import java.util.UUID;

@Service
public class EnrollmentService {
  private final EnrollmentRepository enrollments; private final CourseRepository courses;
  private final UserRepository users; private final PaymentRepository payments;
  public EnrollmentService(EnrollmentRepository enrollments, CourseRepository courses,
                           UserRepository users, PaymentRepository payments) {
    this.enrollments = enrollments; this.courses = courses; this.users = users; this.payments = payments;
  }
  public Enrollment enroll(Long courseId, Long studentId) {
    Course course = courses.findById(courseId).orElseThrow();
    if (course.getStatus() != CourseStatus.APPROVED) throw new RuntimeException("Course not available");
    User student = users.findById(studentId).orElseThrow();
    Enrollment e = new Enrollment(); e.setCourse(course); e.setStudent(student);
    e.setProgress(0.0); e.setStatus(EnrollmentStatus.NOT_STARTED); e = enrollments.save(e);
    Payment p = new Payment(); p.setOrderId(UUID.randomUUID().toString());
    p.setTransactionId(UUID.randomUUID().toString()); p.setStatus("SUCCESS"); p.setEnrollment(e);
    payments.save(p); return e;
  }
  public Enrollment updateProgress(Long enrollmentId, double progress) {
    Enrollment e = enrollments.findById(enrollmentId).orElseThrow();
    e.setProgress(progress);
    if (progress <= 0) e.setStatus(EnrollmentStatus.NOT_STARTED);
    else if (progress >= 100) e.setStatus(EnrollmentStatus.COMPLETED);
    else e.setStatus(EnrollmentStatus.IN_PROGRESS);
    return enrollments.save(e);
  }
  public List<Enrollment> byStudent(Long studentId) { return enrollments.findByStudent_Id(studentId); }
}
