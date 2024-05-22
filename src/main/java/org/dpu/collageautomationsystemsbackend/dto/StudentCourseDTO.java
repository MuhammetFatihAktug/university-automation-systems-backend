package org.dpu.collageautomationsystemsbackend.dto;

public record StudentCourseDTO(
        int id,
        StudentDTO student,
        CourseDTO course,
        int midterm,
        int finalExam,
        int makeup,
        int average,
        String letterGrade,
        String status
) {
}