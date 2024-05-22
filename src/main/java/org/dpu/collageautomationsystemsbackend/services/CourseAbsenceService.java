package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.entities.student.Course;
import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsence;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.repository.CourseAbsenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseAbsenceService {

    private final CourseAbsenceRepository courseAbsenceRepository;
    private final StudentCourseService studentCourseService;

    public List<CourseAbsence> getAllAbsences(Long studentNumber, int courseCode) {
        StudentCourse studentCourse = studentCourseService.getStudentCourseByStudentId(studentNumber, courseCode);

        return courseAbsenceRepository.findByStudentCourse(studentCourse);
    }

    public Optional<CourseAbsence> getAbsenceById(Long id) {
        return courseAbsenceRepository.findById(id);
    }

    public CourseAbsence saveAbsence(CourseAbsence absence, Long studentNumber, int courseCode) {
        StudentCourse studentCourse = studentCourseService.getStudentCourseByStudentId(studentNumber, courseCode);

        Optional<CourseAbsence> optional = courseAbsenceRepository.findByStudentCourseAndDate(studentCourse, absence.getDate());
        if (optional.isPresent()) {
            CourseAbsence courseAbsence = optional.get();
            courseAbsence.setAbsenceStatus(absence.isAbsenceStatus());
            return courseAbsenceRepository.save(courseAbsence);
        }
        absence.setStudentCourse(studentCourse);


        return courseAbsenceRepository.save(absence);
    }


    public void deleteAbsence(Long id) {
        courseAbsenceRepository.deleteById(id);
    }
}
