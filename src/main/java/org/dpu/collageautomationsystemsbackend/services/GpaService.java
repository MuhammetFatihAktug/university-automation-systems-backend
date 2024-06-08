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

import java.util.ArrayList;
import java.util.HashMap;
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


    @Transactional(readOnly = true)
    public List<Map<String, Double>> calculateAndReturnTermGpa(Long studentNumber) {
        Student student = studentRepository.findStudentByStudentNumber(studentNumber)
                .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));

        List<StudentCourse> courses = studentCourseRepository.findByStudent(student);
        Map<String, List<StudentCourse>> groupedCourses = courses.stream()
                .collect(Collectors.groupingBy(studentCourse ->
                        studentCourse.getCreatedDate() + "_" + (studentCourse.getCourse().getSemester() == 1 ? "Guz" : "Bahar")
                ));

        return groupedCourses.entrySet().stream()
                .map(entry -> getTermGpa(entry, student))
                .collect(Collectors.toList());
    }

    private Map<String, Double> getTermGpa(Map.Entry<String, List<StudentCourse>> entry, Student student) {
        String createdDate = entry.getKey();
        List<StudentCourse> courses = entry.getValue();

        Map<Integer, Double> semesterGpas = new HashMap<>();

        for (StudentCourse course : courses) {
            int semester = course.getCourse().getSemester();
            double points = getGPA(course.getLetterGrade());
            double totalPoints = semesterGpas.getOrDefault(semester, 0.0);
            totalPoints += points * course.getCourse().getCredits();
            semesterGpas.put(semester, totalPoints);
        }

        Map<String, Double> gpaMap = new HashMap<>();
        for (Map.Entry<Integer, Double> semesterEntry : semesterGpas.entrySet()) {
            int semester = semesterEntry.getKey();
            double totalPoints = semesterEntry.getValue();
            int totalCredits = courses.stream()
                    .filter(course -> course.getCourse().getSemester() == semester)
                    .mapToInt(course -> course.getCourse().getCredits())
                    .sum();
            double gpa = totalCredits > 0 ? totalPoints / totalCredits : 0;
            gpaMap.put(createdDate, gpa);
        }

        return gpaMap;
    }


    private double getGPA(String letterGrade) {
        return switch (letterGrade) {
            case "AA" -> 4.0;
            case "BA" -> 3.5;
            case "BB" -> 3.0;
            case "CB" -> 2.5;
            case "CC" -> 2.0;
            case "DC" -> 1.5;
            case "DD" -> 1.0;
            case "FD" -> 0.5;
            case "FF" -> 0.0;
            default -> 0.0;
        };
    }
}