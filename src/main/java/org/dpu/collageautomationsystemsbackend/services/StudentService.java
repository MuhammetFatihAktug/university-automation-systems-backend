package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Gpa;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.mappers.StudentMapper;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GpaService gpaService;

    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.toStudent(studentDTO);
        return studentMapper.toStudentDTO(studentRepository.save(student));
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO) {
        Student existingStudent = getStudentById(studentId);
        studentMapper.updateStudentFromDto(studentDTO, existingStudent);
        return studentMapper.toStudentDTO(studentRepository.save(existingStudent));
    }

    @Transactional(readOnly = true)
    public StudentDTO getStudent(Long studentId) {
        return studentMapper.toStudentDTO(getStudentById(studentId));
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toStudentDTO(students);
    }

    @Transactional(readOnly = true)
    public StudentDTO getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(studentMapper::toStudentDTO)
                .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Map<String, Double> getStudentGpa(Long studentId) {
        List<Gpa> gpaList = gpaService.getGpaByStudent(studentId);
        return gpaList.stream()
                .collect(Collectors.toMap(Gpa::getCreatedDate, Gpa::getGpa));
    }

    @Transactional(readOnly = true)
    public Map<String, Double> getStudentGpaSemester(Long studentId) {
        List<Map<String, Double>> gpaList = gpaService.calculateAndReturnTermGpa(studentId);
        return gpaList.stream()
                .flatMap(gpaMap -> gpaMap.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Double::max
                ));
    }


    private Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException("Student not found with id: " + studentId, HttpStatus.NOT_FOUND));
    }
}
