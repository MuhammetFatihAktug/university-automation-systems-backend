package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.SignUpDto;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentDto;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.mappers.StudentMapper;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentMapper studentMapper;

//    public StudentDto registerStudent(SignUpDto signUpDto) {
//        Optional<Student> optionalStudent = studentRepository.findStudentByStudentNumber(1213123L);
//        if (optionalStudent.isPresent()) {
//            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
//        }
//        Student student = studentMapper.signUpToStudent(signUpDto);
//        student.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
//        Student savedStudent = studentRepository.save(student);
//        return studentMapper.toStudentDto(savedStudent);
//    }
}
