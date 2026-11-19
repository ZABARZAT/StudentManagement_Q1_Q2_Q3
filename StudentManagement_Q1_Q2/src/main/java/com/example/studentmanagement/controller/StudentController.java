package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.SharedSkillGroup;
import com.example.studentmanagement.dto.StudentPerformance;
import com.example.studentmanagement.dto.StudentScoresSkillsUpdate;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@Valid @RequestBody Student student) {
        return service.addStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return service.getStudent(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @Valid @RequestBody Student student) {
        return service.updateStudent(id, student);
    }

    // Q3: Update only scores and technical skills of an existing student.
    @PutMapping("/{id}/scores-skills")
    public Student updateScoresAndSkills(
            @PathVariable int id,
            @Valid @RequestBody StudentScoresSkillsUpdate update) {
        return service.updateScoresAndSkills(id, update);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable int id) {
        service.deleteStudent(id);
    }

    @GetMapping("/performance")
    public List<StudentPerformance> getAllPerformance() {
        return service.getAllPerformance();
    }

    @GetMapping("/{id}/performance")
    public StudentPerformance getPerformance(@PathVariable int id) {
        return service.getPerformance(id);
    }

    @GetMapping("/stronger-subject")
    public List<Map<String, Object>> getStrongerSubjects() {
        return service.getStrongerSubjects();
    }

    @GetMapping("/search")
    public List<Student> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @GetMapping("/merit-list")
    public List<Student> getMeritList() {
        return service.getMeritList();
    }

    @GetMapping("/shared-skills")
    public List<SharedSkillGroup> getSharedSkills() {
        return service.getSharedSkills();
    }

    @GetMapping("/programming-improvement")
    public List<Student> getProgrammingImprovementStudents() {
        return service.getProgrammingImprovementStudents();
    }
}
