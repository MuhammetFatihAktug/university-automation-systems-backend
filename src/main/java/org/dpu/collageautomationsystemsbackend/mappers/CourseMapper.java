package org.dpu.collageautomationsystemsbackend.mappers;

import org.dpu.collageautomationsystemsbackend.dto.CourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toCourse(CourseDTO courseDTO);

    void updateCourseFromDTO(CourseDTO courseDTO, @MappingTarget Course course);

}