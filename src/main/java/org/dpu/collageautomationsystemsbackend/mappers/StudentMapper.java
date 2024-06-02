package org.dpu.collageautomationsystemsbackend.mappers;

import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO toStudentDTO(Student student);


    Student toStudent(StudentDTO studentDTO);

    List<StudentDTO> toStudentDTO(List<Student> students);

    @Mapping(target = "studentNumber", ignore = true)
    void updateStudentFromDto(StudentDTO studentDTO, @MappingTarget Student student);
}
