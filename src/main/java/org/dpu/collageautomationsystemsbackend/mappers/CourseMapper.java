package org.dpu.collageautomationsystemsbackend.mappers;

import org.dpu.collageautomationsystemsbackend.dto.CourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDTO toCourseDTO(Course course);

    Course toCourse(CourseDTO courseDTO);
}