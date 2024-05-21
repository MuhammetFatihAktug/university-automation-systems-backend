package org.dpu.collageautomationsystemsbackend.controllers.manager;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentCourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.services.StudentCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentCourses")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    @PostMapping("/{studentId}/save")
    public ResponseEntity<StudentCourse> saveStudentCourse(@RequestBody StudentCourseDTO studentCourseDTO, @PathVariable Long studentId, @RequestParam int courseCode) {
        StudentCourse savedStudentCourse = studentCourseService.saveStudentCourse(studentCourseDTO, studentId, courseCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudentCourse);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<StudentCourse>> getStudentCoursesByStudentId(@PathVariable Long studentId) {
        List<StudentCourse> studentCourses = studentCourseService.getStudentCoursesByStudentId(studentId);
        return ResponseEntity.ok(studentCourses);
    }

    @PutMapping("/{studentId}/update")
    public ResponseEntity<StudentCourse> updateStudentCourse(@PathVariable Long studentId, @RequestParam int courseCode, @RequestBody StudentCourseDTO studentCourseDTO) {
        StudentCourse updatedStudentCourse = studentCourseService.updateStudentCourse(studentId, courseCode, studentCourseDTO);
        return ResponseEntity.ok(updatedStudentCourse);
    }

    @DeleteMapping("/{studentId}/delete")
    public ResponseEntity<Void> deleteStudentCourse(@PathVariable Long studentId,@RequestParam int courseCode) {
        studentCourseService.deleteStudentCourse(studentId,courseCode);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/saveAll")
    public ResponseEntity<List<StudentCourse>> saveAllStudentCourses(@RequestBody List<StudentCourseDTO> studentCourseDTOs, @PathVariable Long studentId) {
        List<StudentCourse> savedStudentCourses = studentCourseService.saveAllStudentCourses(studentCourseDTOs, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudentCourses);
    }
}