package com.example.studentmanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void calculatesAverageAndPerformanceCategory() {
        Student student = new Student(1, "Test Student", "test@example.com",
                "BTech IT", 90.0, 80.0, List.of("Java"));
        assertEquals(85.0, student.calculateAverage());
        assertEquals("Excellent", student.getPerformanceCategory());
    }

    @Test
    void identifiesStrongerSubject() {
        Student student = new Student(2, "Test Student", "test@example.com",
                "BTech IT", 70.0, 80.0, List.of());
        assertEquals("Mathematics", student.getStrongerSubject());
    }
}
