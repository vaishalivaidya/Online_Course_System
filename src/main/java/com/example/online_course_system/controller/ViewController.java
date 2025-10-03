package com.example.online_course_system.controller;

import com.example.online_course_system.entity.Course;
import com.example.online_course_system.entity.User;
import com.example.online_course_system.service.CourseService;
import com.example.online_course_system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ViewController {
    private final CourseService courses;
    private final UserService users;

    public ViewController(CourseService courses, UserService users) {
        this.courses = courses;
        this.users = users;
    }

    // Home page: list approved courses
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("courses", courses.listApproved());
        return "home"; // maps to templates/home.html
    }

    // Instructor: show form
    @GetMapping("/instructor/new-course")
    public String newCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "new-course"; // maps to templates/new-course.html
    }

    // Instructor: handle form submission
    @PostMapping("/instructor/new-course")
    public String createCourse(@ModelAttribute Course course, Authentication auth) {
        User instructor = users.byUsername(auth.getName());
        course.setInstructor(instructor);
        courses.create(course);
        return "redirect:/"; // back to home
    }

    // Student: enroll form
    @GetMapping("/student/enroll/{id}")
    public String enrollForm(@PathVariable Long id, Model model) {
        model.addAttribute("courseId", id);
        return "enroll"; // templates/enroll.html
    }

    @PostMapping("/student/enroll/{id}")
    public String enrollCourse(@PathVariable Long id, Authentication auth) {
        User student = users.byUsername(auth.getName());
        // call enrollment service
        // (you already have enrollments.enroll(courseId, studentId))
        return "redirect:/student/my-enrollments";
    }

    // Student: view enrollments
    @GetMapping("/student/my-enrollments")
    public String myEnrollments(Authentication auth, Model model) {
        User student = users.byUsername(auth.getName());
        model.addAttribute("enrollments", student.getId()); // replace with service call
        return "my-enrollments"; // templates/my-enrollments.html
    }
}
