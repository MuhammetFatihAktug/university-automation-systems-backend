package org.dpu.collageautomationsystemsbackend.repository;


import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findCourseByCourseCode(int courseCode);

}
