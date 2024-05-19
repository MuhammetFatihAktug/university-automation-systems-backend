package org.dpu.collageautomationsystemsbackend.mappers;

import org.dpu.collageautomationsystemsbackend.dto.SignUpDto;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentDto;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toStudentDto(Student student);

    @Mapping(target = "password", ignore = true)
    Student signUpToStudent(SignUpDto signUpDto);

    Student toStudent(StudentDTO studentDTO);
}
