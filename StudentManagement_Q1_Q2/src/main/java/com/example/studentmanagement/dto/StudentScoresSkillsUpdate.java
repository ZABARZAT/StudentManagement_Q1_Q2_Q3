package com.example.studentmanagement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record StudentScoresSkillsUpdate(
        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        Double programmingScore,

        @NotNull @DecimalMin("0.0") @DecimalMax("100.0")
        Double mathematicsScore,

        List<String> technicalSkills
) {}
