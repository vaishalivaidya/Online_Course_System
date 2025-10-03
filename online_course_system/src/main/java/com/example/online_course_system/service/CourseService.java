// service/CourseService.java
package com.example.online_course_system.service;
import com.example.online_course_system.entity.*;
import com.example.online_course_system.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {
  private final CourseRepository courses;
  public CourseService(CourseRepository courses) { this.courses = courses; }
  public Course create(Course c) { return courses.save(c); }
  public Course update(Long id, Course data, Long instructorId) {
    Course c = courses.findById(id).orElseThrow();
    if (!c.getInstructor().getId().equals(instructorId)) throw new RuntimeException("Not your course");
    c.setTitle(data.getTitle()); c.setDescription(data.getDescription());
    c.setDuration(data.getDuration()); c.setFee(data.getFee()); c.setCategory(data.getCategory());
    c.setStatus(CourseStatus.PENDING);
    return courses.save(c);
  }
  public void delete(Long id, Long instructorId) {
    Course c = courses.findById(id).orElseThrow();
    if (!c.getInstructor().getId().equals(instructorId)) throw new RuntimeException("Not your course");
    courses.delete(c);
  }
  public List<Course> listApproved() { return courses.findByStatus(CourseStatus.APPROVED); }
  public List<Course> listPending() { return courses.findByStatus(CourseStatus.PENDING); }
  public Course approve(Long id) { Course c = courses.findById(id).orElseThrow(); c.setStatus(CourseStatus.APPROVED); return courses.save(c); }
  public Course reject(Long id) { Course c = courses.findById(id).orElseThrow(); c.setStatus(CourseStatus.REJECTED); return courses.save(c); }
}
