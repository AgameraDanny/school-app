package com.agamera.school_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double grade;

    // Many Students belong to one School
    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonBackReference // This prevents infinite recursion in JSON
    private School school;

    // Constructors, Getters, Setters
    public Student() {}

    public Student(String name, Double grade, School school) {
        this.name = name;
        this.grade = grade;
        this.school = school;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
}