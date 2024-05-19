package org.dpu.collageautomationsystemsbackend.services;


import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentCourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.mappers.StudentCourseMapper;
import org.dpu.collageautomationsystemsbackend.mappers.StudentMapper;
import org.dpu.collageautomationsystemsbackend.repository.CourseRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentCourseRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final CourseService courseService;
    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final StudentCourseMapper studentCourseMapper;

    public StudentCourse saveStudentCourse(StudentCourseDTO studentCourseDTO, Long studentId, int courseCode) {
        Student student = studentMapper.toStudent(studentService.getStudent(studentId));
        Course course = courseService.getCourseById(courseCode);

        StudentCourse studentCourse = studentCourseMapper.toStudentCourse(studentCourseDTO);
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        return studentCourseRepository.save(studentCourse);
    }

    public List<StudentCourse> getStudentCoursesByStudentId(Long studentId) {
        Student student = studentMapper.toStudent(studentService.getStudent(studentId));
        return studentCourseRepository.findByStudent(student);
    }

    public StudentCourse updateStudentCourse(Long studentId, int courseCode, StudentCourseDTO studentCourseDTO) {
        Student student = studentMapper.toStudent(studentService.getStudent(studentId));

        Course course = courseService.getCourseById(courseCode);

        StudentCourse existingStudentCourse = studentCourseRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new AppException("Student Course not found for student id: " + studentId + " and course code: " + courseCode, HttpStatus.NOT_FOUND));

        studentCourseMapper.updateStudentCourseFromDTO(studentCourseDTO, existingStudentCourse);

        existingStudentCourse.setStudent(student);
        existingStudentCourse.setCourse(course);

        return studentCourseRepository.save(existingStudentCourse);
    }

    public void deleteStudentCourse(Long studentId, int courseCode) {
        Student student = studentMapper.toStudent(studentService.getStudent(studentId));
        Course course = courseService.getCourseById(courseCode);

        Optional<StudentCourse> studentCourse = studentCourseRepository.findByStudentAndCourse(student, course);
        studentCourse.ifPresentOrElse(
                studentCourseRepository::delete,
                () -> {
                    throw new AppException("StudentCourse not found with studentId: " + studentId + " and courseCode: " + courseCode, HttpStatus.NOT_FOUND);
                }
        );
    }

    @Transactional
    public List<StudentCourse> saveAllStudentCourses(List<StudentCourseDTO> studentCourseDTOs, Long studentId) {
        Student student = studentMapper.toStudent(studentService.getStudent(studentId));
        List<StudentCourse> studentCourses = new ArrayList<>();

        for (StudentCourseDTO studentCourseDTO : studentCourseDTOs) {
            int courseCode = studentCourseDTO.course().courseCode();
            Course course = courseService.getCourseById(courseCode);

            StudentCourse studentCourse = studentCourseMapper.toStudentCourse(studentCourseDTO);

            studentCourse.setStudent(student);
            studentCourse.setCourse(course);

            studentCourses.add(studentCourse);
        }

        return studentCourseRepository.saveAll(studentCourses);
    }
}
