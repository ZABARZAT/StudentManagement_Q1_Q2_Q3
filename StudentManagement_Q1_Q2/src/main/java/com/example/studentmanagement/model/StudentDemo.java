package com.example.studentmanagement.model;

import java.util.Arrays;

public class StudentDemo {
    public static void main(String[] args) {
        Student student = new Student(
                101, "Prithvi", "prithvi@gmail.com", "BTech IT",
                85.5, 90.0, Arrays.asList("Java", "Python", "SQL")
        );

        System.out.println("Student details:");
        System.out.println(student);
        System.out.println("Average: " + student.calculateAverage());
        System.out.println("Category: " + student.getPerformanceCategory());
        System.out.println("Stronger subject: " + student.getStrongerSubject());
    }
}
