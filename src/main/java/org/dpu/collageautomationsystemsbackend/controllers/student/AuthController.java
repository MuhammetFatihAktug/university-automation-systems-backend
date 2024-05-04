package org.dpu.collageautomationsystemsbackend.controllers.student;


import org.dpu.collageautomationsystemsbackend.dto.student.StudentCredentialDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "localhost:4200")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StudentCredentialDto credentialDto) {
        //StudentDto user = userService.login(credentialDto);
        return null;
    }
}
