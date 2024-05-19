package org.dpu.collageautomationsystemsbackend.controllers.student;


import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.CourseDTO;
import org.dpu.collageautomationsystemsbackend.dto.StudentCourseDTO;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.mappers.CourseMapper;
import org.dpu.collageautomationsystemsbackend.mappers.StudentCourseMapper;
import org.dpu.collageautomationsystemsbackend.mappers.StudentMapper;
import org.dpu.collageautomationsystemsbackend.repository.CourseRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentCourseRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class StudentController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final StudentCourseMapper studentCourseMapper;

    @PostMapping("/student")
    public ResponseEntity<String> setStudent(@RequestBody StudentDTO studentDTO) {
        Student student = studentMapper.toStudent(studentDTO);
        studentRepository.save(student);
        return ResponseEntity.ok().body("Student saved successfully");
    }

    @PostMapping("/course")
    public ResponseEntity<String> setCourse(@RequestBody CourseDTO courseDTO) {
        Course course = courseMapper.toCourse(courseDTO);
        courseRepository.save(course);
        return ResponseEntity.ok().body("Course saved successfully");
    }

    @PostMapping("/studentCourse")
    public ResponseEntity<String> setStudentCourse(@RequestParam Long studentNumber, @RequestParam int courseCode, @RequestBody StudentCourseDTO studentCourseDTO) {

        return ResponseEntity.ok().body("Student Course saved successfully");
    }
}
