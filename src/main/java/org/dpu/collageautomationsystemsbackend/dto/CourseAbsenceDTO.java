package org.dpu.collageautomationsystemsbackend.dto;

import java.util.Date;

public record CourseAbsenceDTO(
        Long id,
        StudentCourseDTO studentCourseDTO,
        Date date,
        boolean absenceStatus
) {
}
