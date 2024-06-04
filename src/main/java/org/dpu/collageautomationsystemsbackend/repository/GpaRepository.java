package org.dpu.collageautomationsystemsbackend.repository;

import org.dpu.collageautomationsystemsbackend.entities.student.Gpa;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GpaRepository extends JpaRepository<Gpa, Long> {
    List<Gpa> findByStudent(Student student);

    Gpa findByStudentAndCreatedDate(Student student, String createdDate);
}