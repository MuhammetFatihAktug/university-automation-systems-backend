package org.dpu.collageautomationsystemsbackend.services;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsence;
import org.dpu.collageautomationsystemsbackend.entities.student.StudentCourse;
import org.dpu.collageautomationsystemsbackend.repository.CourseAbsenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseAbsenceService {

    private final CourseAbsenceRepository courseAbsenceRepository;
    private final StudentCourseService studentCourseService;

    @Transactional(readOnly = true)
    public List<CourseAbsence> getAllAbsences(Long studentNumber) {
        List<StudentCourse> studentCourses = studentCourseService.getStudentCoursesByStudentId(studentNumber);
        return courseAbsenceRepository.findAllByStudentCourses(studentCourses);
    }

    @Transactional(readOnly = true)
    public List<CourseAbsence> getAbsencesByCourse(Long studentNumber, int courseCode) {
        StudentCourse studentCourse = studentCourseService.getStudentCourseByStudentId(studentNumber, courseCode);
        return courseAbsenceRepository.findByStudentCourse(studentCourse);
    }

    @Transactional
    public CourseAbsence saveAbsence(CourseAbsence absence, Long studentNumber, int courseCode) {
        StudentCourse studentCourse = studentCourseService.getStudentCourseByStudentId(studentNumber, courseCode);
        return courseAbsenceRepository.findByStudentCourseAndDate(studentCourse, absence.getDate())
                .map(existingAbsence -> updateAbsence(existingAbsence, absence))
                .orElseGet(() -> createAbsence(absence, studentCourse));
    }

    @Transactional
    public void deleteAbsenceByDateAndStudentCourse(Long studentNumber, int courseCode, Date date) {
        StudentCourse studentCourse = studentCourseService.getStudentCourseByStudentId(studentNumber, courseCode);
        courseAbsenceRepository.deleteByStudentCourseAndDate(studentCourse, date);
    }

    private CourseAbsence updateAbsence(CourseAbsence existingAbsence, CourseAbsence newAbsence) {
        existingAbsence.setAbsenceStatus(newAbsence.isAbsenceStatus());
        return courseAbsenceRepository.save(existingAbsence);
    }

    private CourseAbsence createAbsence(CourseAbsence absence, StudentCourse studentCourse) {
        absence.setStudentCourse(studentCourse);
        return courseAbsenceRepository.save(absence);
    }
}
