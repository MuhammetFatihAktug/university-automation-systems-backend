package org.dpu.collageautomationsystemsbackend.dto;

public record CourseDTO(
        int courseCode,
        int semester,
        String name,
        String description,
        int credits,
        int ects
) {
}