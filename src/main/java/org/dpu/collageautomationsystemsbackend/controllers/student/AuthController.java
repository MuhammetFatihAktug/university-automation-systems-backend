package org.dpu.collageautomationsystemsbackend.controllers.student;


import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.SignUpDto;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentCredentialDto;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentDto;
import org.dpu.collageautomationsystemsbackend.services.ManagerService;
import org.dpu.collageautomationsystemsbackend.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class AuthController {
    private final StudentService studentService;
    private final ManagerService managerService;

    @PostMapping("/login")
    public ResponseEntity<StudentDto> login(@RequestBody StudentCredentialDto credentialDto) {
        StudentDto user = studentService.login(credentialDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDto> register(@RequestBody SignUpDto signUpDto) {
        StudentDto studentDto = managerService.registerStudent(signUpDto);
        return ResponseEntity.created(URI.create("/students/" + studentDto.getId())).body(studentDto);
    }

}
