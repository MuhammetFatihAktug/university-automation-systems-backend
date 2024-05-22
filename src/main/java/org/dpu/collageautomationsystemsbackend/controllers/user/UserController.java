package org.dpu.collageautomationsystemsbackend.controllers.user;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.services.StudentCourseService;
import org.dpu.collageautomationsystemsbackend.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080","http://192.168.1.6:4200"})
public class UserController {

    private final StudentService studentService;
    private final StudentCourseService studentCourseService;

    @GetMapping("/info")
    public ResponseEntity<StudentDTO> getInfo(@RequestParam("studentNumber") Long studentNumber) {
        StudentDTO user = studentService.getStudent(studentNumber);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<StudentCourse>> getCourses(@RequestParam Long studentNumber) {
        List<StudentCourse> courses = studentCourseService.getStudentCoursesByStudentId(studentNumber);
        return ResponseEntity.ok().body(courses);
    }



}
