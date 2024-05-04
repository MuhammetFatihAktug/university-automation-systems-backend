package org.dpu.collageautomationsystemsbackend.controllers.student;


import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentCredentialDto;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentDto;
import org.dpu.collageautomationsystemsbackend.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:4200")
public class AuthController {
    private final StudentService studentService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StudentCredentialDto credentialDto) {
        StudentDto user = studentService.login(credentialDto);
        return null;
    }
}
