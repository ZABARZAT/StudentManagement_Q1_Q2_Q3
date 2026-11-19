package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByEmailAddressContainingIgnoreCase(String email);

    List<Student> findByCourseNameContainingIgnoreCase(String course);

    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.emailAddress) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.courseName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByEmailOrCourse(@Param("keyword") String keyword);

    List<Student> findByProgrammingScoreLessThanAndMathematicsScoreGreaterThanEqual(
            Double programmingLimit, Double mathematicsMinimum);
}
