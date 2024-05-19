package org.dpu.collageautomationsystemsbackend.repository;


import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findCourseByCourseCode(int courseCode);

}
