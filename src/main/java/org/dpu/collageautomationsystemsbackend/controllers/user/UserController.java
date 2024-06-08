package org.dpu.collageautomationsystemsbackend.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.config.JwtService;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsence;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.56.1:4200"})
public class UserController {

    private final StudentService studentService;
    private final StudentCourseService studentCourseService;
    private final CourseAbsenceService courseAbsenceService;
    private final JwtService jwtService;
    private final GpaService gpaService;

    @GetMapping("/info")
    public ResponseEntity<StudentDTO> getInfo(HttpServletRequest request) {
        String email = extractEmailFromRequest(request);
        StudentDTO student = studentService.getStudentByEmail(email);
        return ResponseEntity.ok().body(student);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<StudentCourse>> getCourses(HttpServletRequest request) {
        Long studentNumber = getStudentNumberFromRequest(request);
        List<StudentCourse> courses = studentCourseService.getStudentCoursesByStudentId(studentNumber);

        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/absences")
    public ResponseEntity<List<CourseAbsence>> getAllAbsences(HttpServletRequest request) {
        Long studentNumber = getStudentNumberFromRequest(request);
        List<CourseAbsence> courseAbsenceList = courseAbsenceService.getAllAbsences(studentNumber);
        return ResponseEntity.ok().body(courseAbsenceList);
    }

    @GetMapping("/gpa")
    public ResponseEntity<Map<String, Double>> getAllGPA(HttpServletRequest request) {
        Long studentNumber = getStudentNumberFromRequest(request);
        Map<String, Double> gpaMap = studentService.getStudentGpa(studentNumber);
        return ResponseEntity.ok(gpaMap);
    }

    @GetMapping("/gpa/semester")
    public ResponseEntity<Map<String, Double>> getGpaSemester(HttpServletRequest request) {
        Long studentNumber = getStudentNumberFromRequest(request);
        Map<String, Double> gpaMap = studentService.getStudentGpaSemester(studentNumber);
        return ResponseEntity.ok(gpaMap);
    }

    private String extractEmailFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException("Token is not valid", HttpStatus.NOT_FOUND);
        }
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
    }

    private Long getStudentNumberFromRequest(HttpServletRequest request) {
        String email = extractEmailFromRequest(request);
        StudentDTO student = studentService.getStudentByEmail(email);
        return student.studentNumber();
    }
}
