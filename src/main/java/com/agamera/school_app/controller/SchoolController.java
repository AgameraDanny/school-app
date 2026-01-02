package com.agamera.school_app.controller;

import com.agamera.school_app.entity.School;
import com.agamera.school_app.entity.Student;
import com.agamera.school_app.repository.SchoolRepository;
import com.agamera.school_app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    // --- GET ---
    @GetMapping("/schools")
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    // --- CREATE ---
    @PostMapping("/schools")
    public School createSchool(@RequestBody School school) {
        return schoolRepository.save(school);
    }

    @PostMapping("/schools/{schoolId}/students")
    public ResponseEntity<Student> addStudent(@PathVariable Long schoolId, @RequestBody Student student) {
        return schoolRepository.findById(schoolId).map(school -> {
            student.setSchool(school);
            return ResponseEntity.ok(studentRepository.save(student));
        }).orElse(ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    @PutMapping("/schools/{id}")
    public ResponseEntity<School> updateSchool(@PathVariable Long id, @RequestBody School schoolDetails) {
        return schoolRepository.findById(id).map(school -> {
            school.setName(schoolDetails.getName());
            school.setPopulation(schoolDetails.getPopulation());
            return ResponseEntity.ok(schoolRepository.save(school));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentRepository.findById(id).map(student -> {
            student.setName(studentDetails.getName());
            student.setGrade(studentDetails.getGrade());
            return ResponseEntity.ok(studentRepository.save(student));
        }).orElse(ResponseEntity.notFound().build());
    }

    // --- DELETE ---
    @DeleteMapping("/schools/{id}")
    public ResponseEntity<?> deleteSchool(@PathVariable Long id) {
        if (!schoolRepository.existsById(id)) return ResponseEntity.notFound().build();
        schoolRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) return ResponseEntity.notFound().build();
        studentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}