package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentCredentialDto;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentDto;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.mappers.StudentMapper;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentMapper studentMapper;

    public StudentDto login(StudentCredentialDto studentCredentialDto) {
        Student student = studentRepository.findByStudentNumber(studentCredentialDto.StudentNumber())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(studentCredentialDto.password()), student.getPassword())) {
            return studentMapper.toStudentDto(student);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

}
