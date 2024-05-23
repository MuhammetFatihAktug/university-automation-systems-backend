package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
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

    public StudentDTO registerStudent(StudentDTO studentDTO) {
        Optional<Student> optionalStudent = studentRepository.findStudentByStudentNumber(studentDTO.studentNumber());
        if (optionalStudent.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        Student student = studentMapper.toStudent(studentDTO);
        student.setPassword(passwordEncoder.encode(CharBuffer.wrap(studentDTO.password())));
        Student savedStudent = studentRepository.save(student);

        return studentMapper.toStudentDTO(savedStudent);
    }
}
