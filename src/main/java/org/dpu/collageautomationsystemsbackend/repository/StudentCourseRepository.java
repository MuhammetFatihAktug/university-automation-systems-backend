package org.dpu.collageautomationsystemsbackend.repository;


import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
}
