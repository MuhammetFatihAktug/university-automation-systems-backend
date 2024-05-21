package org.dpu.collageautomationsystemsbackend.controllers.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class AuthController {
    //private final StudentService studentService;
    //private final ManagerService managerService;
    //private final StudentAuthProvider studentAuthProvider;

//    @PostMapping("/login")
//    public ResponseEntity<StudentDto> login(@RequestBody StudentCredentialDto credentialDto) {
//        StudentDto student = studentService.login(credentialDto);
//        student.setToken(studentAuthProvider.createToken(student));
//        return ResponseEntity.ok(student);
//    }

//    @PostMapping("/register")
//    public ResponseEntity<StudentDto> register(@RequestBody SignUpDto signUpDto) {
//        StudentDto studentDto = managerService.registerStudent(signUpDto);
//        studentDto.setToken(studentAuthProvider.createToken(studentDto));
//        return ResponseEntity.created(URI.create("/students/" + studentDto.getId())).body(studentDto);
//    }



}
