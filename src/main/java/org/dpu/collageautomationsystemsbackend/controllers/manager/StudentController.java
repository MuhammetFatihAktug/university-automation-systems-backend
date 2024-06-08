package org.dpu.collageautomationsystemsbackend.controllers.manager;


import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(studentId, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
}
