package com.example.studentmanagement.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "student_id", nullable = false, updatable = false)
    private int studentId;

    @NotBlank
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @NotBlank
    @Email
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @NotBlank
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Column(name = "programming_score", nullable = false)
    private Double programmingScore;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Column(name = "mathematics_score", nullable = false)
    private Double mathematicsScore;

    @ElementCollection
    @CollectionTable(
            name = "student_skills",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "technical_skill", nullable = false)
    private List<String> technicalSkills = new ArrayList<>();

    public Student() {
        this.technicalSkills = new ArrayList<>();
    }

    public Student(int studentId, String studentName, String emailAddress,
                   String courseName, Double programmingScore,
                   Double mathematicsScore, List<String> technicalSkills) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.emailAddress = emailAddress;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = technicalSkills == null
                ? new ArrayList<>()
                : new ArrayList<>(technicalSkills);
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Double getProgrammingScore() { return programmingScore; }
    public void setProgrammingScore(Double programmingScore) { this.programmingScore = programmingScore; }

    public Double getMathematicsScore() { return mathematicsScore; }
    public void setMathematicsScore(Double mathematicsScore) { this.mathematicsScore = mathematicsScore; }

    public List<String> getTechnicalSkills() {
        return technicalSkills == null ? new ArrayList<>() : new ArrayList<>(technicalSkills);
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills == null
                ? new ArrayList<>()
                : new ArrayList<>(technicalSkills);
    }

    public void addSkill(String skill) {
        if (skill != null && !skill.isBlank()) {
            if (technicalSkills == null) technicalSkills = new ArrayList<>();
            technicalSkills.add(skill.trim());
        }
    }

    public void removeSkill(String skill) {
        if (technicalSkills != null) technicalSkills.remove(skill);
    }

    public double calculateAverage() {
        return (programmingScore + mathematicsScore) / 2.0;
    }

    public String getPerformanceCategory() {
        double average = calculateAverage();
        if (average >= 85) return "Excellent";
        if (average >= 70) return "Good";
        if (average >= 50) return "Satisfactory";
        return "Needs Improvement";
    }

    public String getStrongerSubject() {
        if (programmingScore > mathematicsScore) return "Programming";
        if (mathematicsScore > programmingScore) return "Mathematics";
        return "Both subjects are equal";
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", courseName='" + courseName + '\'' +
                ", programmingScore=" + programmingScore +
                ", mathematicsScore=" + mathematicsScore +
                ", technicalSkills=" + technicalSkills +
                '}';
    }
}
