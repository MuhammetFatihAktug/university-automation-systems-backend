package org.dpu.collageautomationsystemsbackend.controllers.user;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.entities.auth.AuthenticationRequest;
import org.dpu.collageautomationsystemsbackend.entities.auth.AuthenticationResponse;

import org.dpu.collageautomationsystemsbackend.services.AuthService;
import org.dpu.collageautomationsystemsbackend.services.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class AuthController {

    private final ManagerService managerService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(authService.login(authenticationRequest));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody StudentDTO studentDTO) {
        StudentDTO registeredStudentDTO = managerService.registerStudent(studentDTO);
        return ResponseEntity.ok("Successfully");
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }

}
