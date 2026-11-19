package com.example.studentmanagement.dto;

import java.util.List;

public record SharedSkillGroup(String skill, List<Integer> studentIds, List<String> studentNames) {}
