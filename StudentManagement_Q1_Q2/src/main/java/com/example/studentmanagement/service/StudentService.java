package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.SharedSkillGroup;
import com.example.studentmanagement.dto.StudentPerformance;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student addStudent(Student student) {
        if (repository.existsById(student.getStudentId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A student with this ID already exists.");
        }
        return repository.save(student);
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Student getStudent(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found: " + id));
    }

    public Student updateStudent(int id, Student updated) {
        Student existing = getStudent(id);
        existing.setStudentName(updated.getStudentName());
        existing.setEmailAddress(updated.getEmailAddress());
        existing.setCourseName(updated.getCourseName());
        existing.setProgrammingScore(updated.getProgrammingScore());
        existing.setMathematicsScore(updated.getMathematicsScore());
        existing.setTechnicalSkills(updated.getTechnicalSkills());
        return repository.save(existing);
    }

    public Student updateScoresAndSkills(int id,
            com.example.studentmanagement.dto.StudentScoresSkillsUpdate update) {
        Student existing = getStudent(id);
        existing.setProgrammingScore(update.programmingScore());
        existing.setMathematicsScore(update.mathematicsScore());
        existing.setTechnicalSkills(update.technicalSkills());
        return repository.save(existing);
    }

    public void deleteStudent(int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public StudentPerformance getPerformance(int id) {
        return toPerformance(getStudent(id));
    }

    @Transactional(readOnly = true)
    public List<StudentPerformance> getAllPerformance() {
        return repository.findAll().stream().map(this::toPerformance).toList();
    }

    @Transactional(readOnly = true)
    public List<Student> getMeritList() {
        return repository.findAll().stream()
                .sorted(Comparator.comparingDouble(Student::calculateAverage).reversed())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getStrongerSubjects() {
        return repository.findAll().stream().map(student -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("studentId", student.getStudentId());
            row.put("studentName", student.getStudentName());
            row.put("strongerSubject", student.getStrongerSubject());
            row.put("programmingScore", student.getProgrammingScore());
            row.put("mathematicsScore", student.getMathematicsScore());
            return row;
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return List.of();
        String term = keyword.trim();
        List<Student> results = new ArrayList<>(repository.searchByEmailOrCourse(term));
        try {
            int id = Integer.parseInt(term);
            repository.findById(id).ifPresent(student -> {
                if (results.stream().noneMatch(s -> s.getStudentId() == student.getStudentId())) {
                    results.add(student);
                }
            });
        } catch (NumberFormatException ignored) {
            // A non-numeric search term is matched against email and course.
        }
        return results;
    }

    @Transactional(readOnly = true)
    public List<SharedSkillGroup> getSharedSkills() {
        Map<String, List<Student>> bySkill = new LinkedHashMap<>();
        for (Student student : repository.findAll()) {
            for (String skill : student.getTechnicalSkills()) {
                bySkill.computeIfAbsent(skill.toLowerCase(), key -> new ArrayList<>()).add(student);
            }
        }
        return bySkill.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= 2)
                .map(entry -> new SharedSkillGroup(
                        entry.getValue().get(0).getTechnicalSkills().stream()
                                .filter(s -> s.equalsIgnoreCase(entry.getKey()))
                                .findFirst().orElse(entry.getKey()),
                        entry.getValue().stream().map(Student::getStudentId).toList(),
                        entry.getValue().stream().map(Student::getStudentName).toList()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Student> getProgrammingImprovementStudents() {
        // "Needs improvement" is interpreted as programming score below 50.
        return repository.findByProgrammingScoreLessThanAndMathematicsScoreGreaterThanEqual(
                50.0, 75.0);
    }

    private StudentPerformance toPerformance(Student student) {
        return new StudentPerformance(
                student.getStudentId(),
                student.getStudentName(),
                student.getProgrammingScore(),
                student.getMathematicsScore(),
                student.calculateAverage(),
                student.getPerformanceCategory()
        );
    }
}
