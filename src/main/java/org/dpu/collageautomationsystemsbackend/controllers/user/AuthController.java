package org.dpu.collageautomationsystemsbackend.controllers.user;


import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.config.StudentAuthProvider;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.dto.StudentCredentialDTO;
import org.dpu.collageautomationsystemsbackend.services.AuthService;
import org.dpu.collageautomationsystemsbackend.services.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class AuthController {

    private final AuthService authService;
    private final ManagerService managerService;
    private final StudentAuthProvider studentAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StudentCredentialDTO credentialDto) {
        StudentDTO student = authService.login(credentialDto);
        return ResponseEntity.ok(studentAuthProvider.createToken(student));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody StudentDTO studentDTO) {
        StudentDTO registeredStudentDTO = managerService.registerStudent(studentDTO);
        return ResponseEntity.ok(studentAuthProvider.createToken(studentDTO));
    }


}
