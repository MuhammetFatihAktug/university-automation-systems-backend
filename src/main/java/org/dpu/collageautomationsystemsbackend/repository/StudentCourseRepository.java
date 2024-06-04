package org.dpu.collageautomationsystemsbackend.repository;


import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    List<StudentCourse> findByStudent(Student student);

    Optional<StudentCourse> findByStudentAndCourse(Student student, Course course);

    List<StudentCourse> findByStudentAndCreatedDate(Student student, String createdDate);


}
