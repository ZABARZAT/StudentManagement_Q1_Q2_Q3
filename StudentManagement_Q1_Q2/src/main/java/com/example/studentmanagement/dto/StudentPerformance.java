package com.example.studentmanagement.dto;

public record StudentPerformance(
        int studentId,
        String studentName,
        double programmingScore,
        double mathematicsScore,
        double averageScore,
        String performanceCategory
) {}
