package org.dpu.collageautomationsystemsbackend.mappers;

import org.dpu.collageautomationsystemsbackend.dto.StudentCourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface StudentCourseMapper {

    StudentCourseDTO toStudentCourseDTO(StudentCourse studentCourse);

    StudentCourse toStudentCourse(StudentCourseDTO studentCourseDTO);

    @Mapping(target = "id", ignore = true) // Ensure the id is not changed
    void updateStudentCourseFromDTO(StudentCourseDTO dto, @MappingTarget StudentCourse entity);
}