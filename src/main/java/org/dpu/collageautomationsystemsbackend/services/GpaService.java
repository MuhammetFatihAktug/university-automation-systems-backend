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
        Student student = findStudentByStudentNumber(studentNumber);
        return gpaRepository.findByStudent(student);
    }

    @Transactional(readOnly = true)
    public Gpa getGpaByStudentAndCreatedDate(Long studentNumber, String createdDate) {
        Student student = findStudentByStudentNumber(studentNumber);
        return gpaRepository.findByStudentAndCreatedDate(student, createdDate);
    }

    @Transactional
    public void calculateAndSaveGpa(Long studentNumber) {
        Student student = findStudentByStudentNumber(studentNumber);
        List<StudentCourse> courses = studentCourseRepository.findByStudent(student);

        Map<String, List<StudentCourse>> groupedCourses = groupCoursesByCreatedDate(courses);
        groupedCourses.forEach((createdDate, courseList) -> {
            Gpa gpaEntity = calculateGpa(courseList, student, createdDate);
            gpaRepository.save(gpaEntity);
        });
    }

    @Transactional(readOnly = true)
    public List<Map<String, Double>> calculateAndReturnTermGpa(Long studentNumber) {
        Student student = findStudentByStudentNumber(studentNumber);
        List<StudentCourse> courses = studentCourseRepository.findByStudent(student);

        Map<String, List<StudentCourse>> groupedCourses = groupCoursesByTerm(courses);
        return groupedCourses.entrySet().stream()
                .map(entry -> calculateTermGpa(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Student findStudentByStudentNumber(Long studentNumber) {
        return studentRepository.findStudentByStudentNumber(studentNumber)
                .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));
    }

    private Map<String, List<StudentCourse>> groupCoursesByCreatedDate(List<StudentCourse> courses) {
        return courses.stream()
                .collect(Collectors.groupingBy(StudentCourse::getCreatedDate));
    }

    private Map<String, List<StudentCourse>> groupCoursesByTerm(List<StudentCourse> courses) {
        return courses.stream()
                .collect(Collectors.groupingBy(course ->
                        course.getCreatedDate() + "_" + (course.getCourse().getSemester() == 1 ? "Guz" : "Bahar")
                ));
    }

    private Gpa calculateGpa(List<StudentCourse> courseList, Student student, String createdDate) {
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

    private Map<String, Double> calculateTermGpa(String term, List<StudentCourse> courses) {
        Map<Integer, Double> semesterPoints = new HashMap<>();
        Map<Integer, Integer> semesterCredits = new HashMap<>();

        for (StudentCourse course : courses) {
            int semester = course.getCourse().getSemester();
            double points = getGPA(course.getLetterGrade()) * course.getCourse().getCredits();
            semesterPoints.merge(semester, points, Double::sum);
            semesterCredits.merge(semester, course.getCourse().getCredits(), Integer::sum);
        }

        Map<String, Double> termGpaMap = new HashMap<>();
        semesterPoints.forEach((semester, points) -> {
            int credits = semesterCredits.getOrDefault(semester, 0);
            double gpa = credits > 0 ? points / credits : 0;
            termGpaMap.put(term, gpa);
        });

        return termGpaMap;
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
