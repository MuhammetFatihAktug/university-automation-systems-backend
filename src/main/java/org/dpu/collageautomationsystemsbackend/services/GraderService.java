package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.repository.StudentCourseRepository;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GraderService {
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;

    public StudentCourse calculateCourseDetails(StudentCourse course) {
        long average;
        if (course.getFinalExam() < 50) {
            average = (long) ((course.getMidterm() * 0.4) + (course.getMakeup() * 0.6));
        } else {
            average = (long) ((course.getMidterm() * 0.4) + (course.getFinalExam() * 0.6));
        }

        String letterGrade = getLetterGrade(average);
        String status = average >= 60 ? "passed" : "failed";

        course.setAverage(average);
        course.setLetterGrade(letterGrade);
        course.setStatus(status);

        return course;
    }


    public List<Double> calculateGPA(Long studentNumber) {
        Optional<Student> student = studentRepository.findStudentByStudentNumber(studentNumber);
        if (student.isPresent()) {

            List<StudentCourse> courses = studentCourseRepository.findByStudent(student.get());
            Map<String, List<StudentCourse>> groupedCourses = courses.stream()
                    .collect(Collectors.groupingBy(StudentCourse::getCreatedDate));

            return getGpaList(groupedCourses);
        }
        throw new AppException("Student not valid ", HttpStatus.NOT_FOUND);
    }

    private List<Double> getGpaList(Map<String, List<StudentCourse>> groupedCourses) {
        List<Double> gpaList = new ArrayList<>();

        for (Map.Entry<String, List<StudentCourse>> entry : groupedCourses.entrySet()) {
            List<StudentCourse> courseList = entry.getValue();
            double totalPoints = 0;
            int totalCredits = 0;

            for (StudentCourse course : courseList) {
                double points = getGPA(course.getLetterGrade());
                totalPoints += points * course.getCourse().getCredits();
                totalCredits += course.getCourse().getCredits();
            }

            double gpa = totalCredits > 0 ? totalPoints / totalCredits : 0;
            gpaList.add(gpa);
        }
        return gpaList;
    }

    private String getLetterGrade(double average) {
        if (average >= 92) return "AA";
        if (average >= 85) return "BA";
        if (average >= 78) return "BB";
        if (average >= 71) return "CB";
        if (average >= 64) return "CC";
        if (average >= 57) return "DC";
        if (average >= 50) return "DD";
        if (average >= 40) return "FD";
        return "FF";
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