package org.dpu.collageautomationsystemsbackend.mappers;

import org.dpu.collageautomationsystemsbackend.dto.student.StudentDto;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toStudentDto(Student student);
}
