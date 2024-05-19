package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.CourseDTO;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.dpu.collageautomationsystemsbackend.exception.AppException;
import org.dpu.collageautomationsystemsbackend.mappers.CourseMapper;
import org.dpu.collageautomationsystemsbackend.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public Course saveCourse(CourseDTO courseDTO) {

        Course existingCourse = courseRepository.findCourseByCourseCode(courseDTO.courseCode()).orElse(null);
        if (existingCourse != null) {
            throw new AppException("Course already exists", HttpStatus.BAD_REQUEST);
        }

        Course newCourse = courseMapper.toCourse(courseDTO);
        return courseRepository.save(newCourse);
    }
    public List<Course> saveAllCourses(List<CourseDTO> courseDTOs) {
        List<Course> courses = courseDTOs.stream()
                .map(courseMapper::toCourse)
                .collect(Collectors.toList());

        return courseRepository.saveAll(courses);
    }

    public Course getCourseById(int courseCode) {
        return courseRepository.findCourseByCourseCode(courseCode)
                .orElseThrow(() -> new AppException("Course not found with code: " + courseCode, HttpStatus.NOT_FOUND));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course updateCourse(int courseCode, CourseDTO courseDTO) {
        Course existingCourse = getCourseById(courseCode);
        Course updatedCourse = courseMapper.toCourse(courseDTO);
        updatedCourse.setCourseCode(existingCourse.getCourseCode());
        return courseRepository.save(updatedCourse);
    }

    public void deleteCourse(int courseCode) {
        Course course = getCourseById(courseCode);
        courseRepository.delete(course);
    }
}
