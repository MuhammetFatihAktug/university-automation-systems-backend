package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.mappers.StudentMapper;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public Student createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.toStudent(studentDTO);
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public Student updateStudent(Long studentId, StudentDTO studentDTO) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException("Student not found with id: " + studentId, HttpStatus.NOT_FOUND));

        student = studentMapper.toStudent(studentDTO);

        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public StudentDTO getStudent(Long studentId) {
        Student student = studentRepository.findStudentByStudentNumber(studentId).orElseThrow(() -> new AppException("Student not found ", HttpStatus.NOT_FOUND));
        return studentMapper.toStudentDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toStudentDTO(students);
    }
}
