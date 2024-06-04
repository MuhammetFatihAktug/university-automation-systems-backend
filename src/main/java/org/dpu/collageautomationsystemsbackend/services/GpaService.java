package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.entities.student.Gpa;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.repository.GpaRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentCourseRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GpaService {

    private final GpaRepository gpaRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    @Transactional(readOnly = true)
    public List<Gpa> getGpaByStudent(Long studentNumber) {
        Student student = studentRepository.findStudentByStudentNumber(studentNumber)
                .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));
        return gpaRepository.findByStudent(student);
    }

    @Transactional(readOnly = true)
    public Gpa getGpaByStudentAndCreatedDate(Long studentNumber, String createdDate) {
        Student student = studentRepository.findStudentByStudentNumber(studentNumber)
                .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));
        return gpaRepository.findByStudentAndCreatedDate(student, createdDate);
    }

    @Transactional
    public void calculateAndSaveGpa(Long studentNumber) {
        Student student = studentRepository.findStudentByStudentNumber(studentNumber)
                .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));

        List<StudentCourse> courses = studentCourseRepository.findByStudent(student);
        Map<String, List<StudentCourse>> groupedCourses = courses.stream()
                .collect(Collectors.groupingBy(StudentCourse::getCreatedDate));

        for (Map.Entry<String, List<StudentCourse>> entry : groupedCourses.entrySet()) {
            Gpa gpaEntity = getGpa(entry, student);

            gpaRepository.save(gpaEntity);
        }
    }

    private Gpa getGpa(Map.Entry<String, List<StudentCourse>> entry, Student student) {
        String createdDate = entry.getKey();
        List<StudentCourse> courseList = entry.getValue();
        double totalPoints = 0;
        int totalCredits = 0;

        for (StudentCourse course : courseList) {
            double points = getGPA(course.getLetterGrade());
            totalPoints += points * course.getCourse().getCredits();
            totalCredits += course.getCourse().getCredits();
        }

        double gpa = totalCredits > 0 ? totalPoints / totalCredits : 0;
        Gpa gpaEntity = new Gpa();
        gpaEntity.setStudent(student);
        gpaEntity.setCreatedDate(createdDate);
        gpaEntity.setGpa(gpa);
        return gpaEntity;
    }

    private double getGPA(String letterGrade) {
        switch (letterGrade) {
            case "AA":
                return 4.0;
            case "BA":
                return 3.5;
            case "BB":
                return 3.0;
            case "CB":
                return 2.5;
            case "CC":
                return 2.0;
            case "DC":
                return 1.5;
            case "DD":
                return 1.0;
            case "FD":
                return 0.5;
            case "FF":
                return 0.0;
            default:
                return 0.0;
        }
    }
}