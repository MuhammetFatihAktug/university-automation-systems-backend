package org.dpu.collageautomationsystemsbackend.repository;

import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsence;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CourseAbsenceRepository extends JpaRepository<CourseAbsence, Long> {
    Optional<CourseAbsence> findByDate(Date date);

    List<CourseAbsence> findByStudentCourse(StudentCourse studentCourse);

    Optional<CourseAbsence> findByStudentCourseAndDate(StudentCourse studentCourse, Date date);
}
