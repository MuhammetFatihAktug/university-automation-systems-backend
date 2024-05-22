package org.dpu.collageautomationsystemsbackend.mappers;

import org.dpu.collageautomationsystemsbackend.dto.CourseAbsenceDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CourseAbsenceMapper {
    CourseAbsenceDTO toCourseAbsenceDTO(CourseAbsence courseAbsence);

    CourseAbsence toCourseAbsence(CourseAbsenceDTO courseAbsence);
}
