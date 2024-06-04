package org.dpu.collageautomationsystemsbackend.dto;

import java.util.Date;

public record StudentCourseDTO(
        int id,
        StudentDTO student,
        CourseDTO course,
        Long midterm,
        Long finalExam,
        Long makeup,
        Long average,
        String letterGrade,
        String status,
        String createdDate) {
}