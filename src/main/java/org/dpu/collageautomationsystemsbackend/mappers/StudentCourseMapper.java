package org.dpu.collageautomationsystemsbackend.mappers;

import org.dpu.collageautomationsystemsbackend.dto.StudentCourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface StudentCourseMapper {

    StudentCourseDTO toStudentCourseDTO(StudentCourse studentCourse);

    StudentCourse toStudentCourse(StudentCourseDTO studentCourseDTO);
}