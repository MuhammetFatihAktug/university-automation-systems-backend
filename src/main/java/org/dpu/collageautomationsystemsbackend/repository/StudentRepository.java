package org.dpu.collageautomationsystemsbackend.repository;

import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByStudentNumber(Long student_id);


}
