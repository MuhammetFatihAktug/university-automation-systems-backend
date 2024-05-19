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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<StudentCourse> getStudentCoursesByStudentId(Long studentId) {
        Student student = studentRepository.findStudentByStudentNumber(studentId)
                .orElseThrow(() -> new AppException("Student not found with id: " + studentId, HttpStatus.NOT_FOUND));
        return studentCourseRepository.findByStudent(student);
    }

    public StudentCourse updateStudentCourse(Long studentCourseId, StudentCourseDTO studentCourseDTO, Long studentId, int courseCode) {
        StudentCourse existingStudentCourse = studentCourseRepository
                .findById(studentCourseId)
                .orElseThrow(() -> new AppException("Student Course not found with id: " + studentCourseId, HttpStatus.NOT_FOUND));

        Student student = studentRepository.findStudentByStudentNumber(studentId)
                .orElseThrow(() -> new AppException("Student not found with id: " + studentId, HttpStatus.NOT_FOUND));

        Course course = courseRepository.findCourseByCourseCode(courseCode)
                .orElseThrow(() -> new AppException("Course not found with code: " + courseCode, HttpStatus.NOT_FOUND));

        existingStudentCourse = studentCourseMapper.toStudentCourse(studentCourseDTO);
        existingStudentCourse.setStudent(student);
        existingStudentCourse.setCourse(course);
        return studentCourseRepository.save(existingStudentCourse);
    }

    public void deleteStudentCourse(Long studentCourseId) {
        studentCourseRepository.deleteById(studentCourseId);
    }

    @Transactional
    public List<StudentCourse> saveAllStudentCourses(List<StudentCourseDTO> studentCourseDTOs, Long studentId) {
        Student student = studentRepository.findStudentByStudentNumber(studentId)
                .orElseThrow(() -> new AppException("Student not found with id: " + studentId, HttpStatus.NOT_FOUND));

        List<StudentCourse> studentCourses = new ArrayList<>();
        for (StudentCourseDTO studentCourseDTO : studentCourseDTOs) {
            Course course = courseRepository.findCourseByCourseCode(studentCourseDTO.course().courseCode())
                    .orElseThrow(() -> new AppException("Course not found with code: " + studentCourseDTO.course().courseCode(), HttpStatus.NOT_FOUND));

            StudentCourse studentCourse = studentCourseMapper.toStudentCourse(studentCourseDTO);
            studentCourse.setStudent(student);
            studentCourse.setCourse(course);
            studentCourses.add(studentCourse);
        }

        return studentCourseRepository.saveAll(studentCourses);
    }
}
