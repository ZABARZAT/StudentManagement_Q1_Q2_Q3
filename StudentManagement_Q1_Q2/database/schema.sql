CREATE DATABASE IF NOT EXISTS student_management;
USE student_management;

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(150) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    course_name VARCHAR(150) NOT NULL,
    programming_score DECIMAL(5,2) NOT NULL,
    mathematics_score DECIMAL(5,2) NOT NULL,
    CONSTRAINT chk_programming_score CHECK (programming_score BETWEEN 0 AND 100),
    CONSTRAINT chk_mathematics_score CHECK (mathematics_score BETWEEN 0 AND 100)
);

CREATE TABLE IF NOT EXISTS student_skills (
    student_id INT NOT NULL,
    technical_skill VARCHAR(100) NOT NULL,
    CONSTRAINT fk_student_skills_student
        FOREIGN KEY (student_id) REFERENCES students(student_id)
        ON DELETE CASCADE
);
