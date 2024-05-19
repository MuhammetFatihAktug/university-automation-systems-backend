package org.dpu.collageautomationsystemsbackend.controllers.student;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.CourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.dpu.collageautomationsystemsbackend.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO courseDTO) {
        Course newCourse = courseService.saveCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
    }

    @PostMapping("/createAll")
    public ResponseEntity<List<Course>> createAllCourses(@RequestBody List<CourseDTO> courseDTOs) {
        List<Course> savedCourses = courseService.saveAllCourses(courseDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourses);
    }

    @GetMapping("/{courseCode}")
    public ResponseEntity<Course> getCourse(@PathVariable int courseCode) {
        Course course = courseService.getCourseById(courseCode);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{courseCode}")
    public ResponseEntity<Course> updateCourse(@PathVariable int courseCode, @RequestBody CourseDTO courseDTO) {
        Course updatedCourse = courseService.updateCourse(courseCode, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{courseCode}")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable int courseCode) {
        courseService.deleteCourse(courseCode);
        return ResponseEntity.ok(true);
    }
}