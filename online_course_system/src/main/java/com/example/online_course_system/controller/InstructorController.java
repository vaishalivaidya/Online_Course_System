// controller/InstructorController.java
package com.example.online_course_system.controller;
import com.example.online_course_system.entity.Course;
import com.example.online_course_system.entity.User;
import com.example.online_course_system.service.CourseService;
import com.example.online_course_system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/instructor")
public class InstructorController {
  private final CourseService courses; private final UserService users;
  public InstructorController(CourseService courses, UserService users) { this.courses = courses; this.users = users; }
  @PostMapping("/courses")
  public Course create(@RequestBody Course c, Authentication auth) {
    User instructor = users.findByUsername(auth.getName()).orElseThrow();
    c.setInstructor(instructor); return courses.create(c);
  }
  @PutMapping("/courses/{id}")
  public Course update(@PathVariable Long id, @RequestBody Course payload, Authentication auth) {
    User instructor = users.findByUsername(auth.getName()).orElseThrow();
    return courses.update(id, payload, instructor.getId());
  }
  @DeleteMapping("/courses/{id}")
  public void delete(@PathVariable Long id, Authentication auth) {
    User instructor = users.findByUsername(auth.getName()).orElseThrow();
    courses.delete(id, instructor.getId());
  }
}
