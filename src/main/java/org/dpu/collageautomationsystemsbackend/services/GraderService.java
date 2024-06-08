package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraderService {

    private static final double MIDTERM_WEIGHT = 0.4;
    private static final double FINAL_EXAM_WEIGHT = 0.6;
    private static final double MAKEUP_EXAM_WEIGHT = 0.6;

    public StudentCourse calculateCourseDetails(StudentCourse course) {
        double finalScore = course.getFinalExam() < 50 ? course.getMakeup() : course.getFinalExam();
        long average = Math.round((course.getMidterm() * MIDTERM_WEIGHT) + (finalScore * FINAL_EXAM_WEIGHT));

        String letterGrade = getLetterGrade(average);
        String status = average >= 50 ? "Basarili" : "Basarisiz";

        course.setAverage(average);
        course.setLetterGrade(letterGrade);
        course.setStatus(status);

        return course;
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
