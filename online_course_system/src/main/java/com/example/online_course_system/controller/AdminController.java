// controller/AdminController.java
package com.example.online_course_system.controller;
import com.example.online_course_system.entity.Course;
import com.example.online_course_system.service.CourseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/admin")
public class AdminController {
  private final CourseService courses;
  public AdminController(CourseService courses) { this.courses = courses; }
  @GetMapping("/courses/pending") public List<Course> pending() { return courses.listPending(); }
  @PutMapping("/courses/{id}/approve") public Course approve(@PathVariable Long id) { return courses.approve(id); }
  @PutMapping("/courses/{id}/reject") public Course reject(@PathVariable Long id) { return courses.reject(id); }
}
