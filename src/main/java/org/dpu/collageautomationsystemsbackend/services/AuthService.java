package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.dto.StudentCredentialDTO;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final StudentService studentService;

    public StudentDTO login(StudentCredentialDTO studentCredentialDTO) {
        try {
            StudentDTO studentDTO = studentService.getStudent(Long.parseLong(studentCredentialDTO.studentNumber()));
            if (passwordEncoder.matches(CharBuffer.wrap(studentCredentialDTO.password()), studentDTO.password())) {
                return studentDTO;
            } else {
                throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new AppException("Invalid Student Number", HttpStatus.BAD_REQUEST);
        }

    }


}
