package org.dpu.collageautomationsystemsbackend.controllers.manager;

import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.entities.student.CourseAbsence;
import org.dpu.collageautomationsystemsbackend.services.CourseAbsenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/absences")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.6:8080"})
public class CourseAbsenceController {

    private final CourseAbsenceService courseAbsenceService;

    @GetMapping("/{studentNumber}/all")
    public List<CourseAbsence> getAllAbsences(@PathVariable("studentNumber") Long studentNumber, @RequestParam("courseCode") int courseCode) {
        return courseAbsenceService.getAllAbsences(studentNumber, courseCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseAbsence> getAbsenceById(@PathVariable Long id) {
        Optional<CourseAbsence> absence = courseAbsenceService.getAbsenceById(id);
        return absence.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{studentNumber}/save")
    public CourseAbsence createAbsence(@PathVariable("studentNumber") Long studentNumber, @RequestParam("courseCode") int courseCode, @RequestBody CourseAbsence absence) {
        return courseAbsenceService.saveAbsence(absence, studentNumber, courseCode);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<CourseAbsence> updateAbsence(@PathVariable Long id, @RequestBody CourseAbsence absenceDetails) {
//        Optional<CourseAbsence> absence = courseAbsenceService.getAbsenceById(id);
//
//        if (absence.isPresent()) {
//            CourseAbsence existingAbsence = absence.get();
//            existingAbsence.setStudentCourse(absenceDetails.getStudentCourse());
//            existingAbsence.setDate(absenceDetails.getDate());
//            existingAbsence.setAbsenceStatus(absenceDetails.isAbsenceStatus());
//            return ResponseEntity.ok(courseAbsenceService.saveAbsence(existingAbsence));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        courseAbsenceService.deleteAbsence(id);
        return ResponseEntity.noContent().build();
    }
}