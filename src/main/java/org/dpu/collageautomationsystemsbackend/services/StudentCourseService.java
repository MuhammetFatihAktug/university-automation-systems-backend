package org.dpu.collageautomationsystemsbackend.services;


import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentCourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.mappers.StudentCourseMapper;
import org.dpu.collageautomationsystemsbackend.repository.CourseRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentCourseRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseMapper studentCourseMapper;

    public StudentCourse saveStudentCourse(StudentCourseDTO studentCourseDTO, Long studentId, int courseCode) {
        Student student = studentRepository.findStudentByStudentNumber(studentId)
                .orElseThrow(() -> new AppException("Student not found with id: " + studentId, HttpStatus.NOT_FOUND));

        Course course = courseRepository.findCourseByCourseCode(courseCode)
                .orElseThrow(() -> new AppException("Course not found with code: " + courseCode, HttpStatus.NOT_FOUND));

        StudentCourse studentCourse = studentCourseMapper.toStudentCourse(studentCourseDTO);
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        return studentCourseRepository.save(studentCourse);
    }

    public List<StudentCourse> getAllStudentCourses() {
        return studentCourseRepository.findAll();
    }

    public StudentCourse getStudentCourseById(Long studentCourseId) {
        return studentCourseRepository.findById(studentCourseId)
                .orElseThrow(() -> new AppException("Student course not found with id: " + studentCourseId,HttpStatus.NOT_FOUND));
    }

    public StudentCourse updateStudentCourse(Long studentCourseId, StudentCourseDTO studentCourseDTO) {
        StudentCourse existingStudentCourse = getStudentCourseById(studentCourseId);
        StudentCourse updatedStudentCourse = studentCourseMapper.toStudentCourse(studentCourseDTO);
        updatedStudentCourse.setId(existingStudentCourse.getId());
        return studentCourseRepository.save(updatedStudentCourse);
    }

    public void deleteStudentCourse(Long studentCourseId) {
        studentCourseRepository.deleteById(studentCourseId);
    }

    public List<StudentCourse> saveAllStudentCourses(List<StudentCourseDTO> studentCourseDTOs, Long studentId, int courseCode) {
        List<StudentCourse> studentCourses = studentCourseDTOs.stream()
                .map(studentCourseDTO -> saveStudentCourse(studentCourseDTO, studentId, courseCode))
                .collect(Collectors.toList());
        return studentCourseRepository.saveAll(studentCourses);
    }
}
