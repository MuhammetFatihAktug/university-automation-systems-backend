package org.dpu.collageautomationsystemsbackend.services;


import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentCourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.mappers.StudentCourseMapper;
import org.dpu.collageautomationsystemsbackend.mappers.StudentMapper;
import org.dpu.collageautomationsystemsbackend.repository.StudentCourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final CourseService courseService;
    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final StudentCourseMapper studentCourseMapper;
    private final GraderService graderService;
    private final GpaService gpaService;

    @Transactional
    public StudentCourse saveStudentCourse(StudentCourseDTO studentCourseDTO, Long studentId, int courseCode) {

        Student student = studentMapper.toStudent(studentService.getStudent(studentId));
        Course course = courseService.getCourseById(courseCode);
        StudentCourse studentCourse = studentCourseMapper.toStudentCourse(studentCourseDTO);
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        return studentCourseRepository.save(graderService.calculateCourseDetails(studentCourse));
    }

    @Transactional
    public List<StudentCourse> getStudentCoursesByStudentId(Long studentId) {
        Student student = studentMapper.toStudent(studentService.getStudent(studentId));
        return studentCourseRepository.findByStudent(student);
    }

    @Transactional
    public StudentCourse getStudentCourseByStudentId(Long studentId, int courseCode) {
        Student student = studentMapper.toStudent(studentService.getStudent(studentId));
        Course course = courseService.getCourseById(courseCode);

        return studentCourseRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new AppException("Student Course not found for student id: " + studentId + " and course code: " + courseCode, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public StudentCourse updateStudentCourse(Long studentId, int courseCode, StudentCourseDTO studentCourseDTO) {
        StudentCourse existingStudentCourse = getStudentCourseByStudentId(studentId, courseCode);

        studentCourseMapper.updateStudentCourseFromDTO(studentCourseDTO, existingStudentCourse);


        return studentCourseRepository.save(existingStudentCourse);
    }

    @Transactional
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

            studentCourses.add(graderService.calculateCourseDetails(studentCourse));
        }
        List<StudentCourse> list = studentCourseRepository.saveAll(studentCourses);
        gpaService.calculateAndSaveGpa(studentId);
        return list;
    }

    public List<StudentCourse> findByStudentIdAndCreatedDate(Long studentNumber, String createdDate) {
        Student student = studentMapper.toStudent(studentService.getStudent(studentNumber));
        return studentCourseRepository.findByStudentAndCreatedDate(student, createdDate);
    }
}
