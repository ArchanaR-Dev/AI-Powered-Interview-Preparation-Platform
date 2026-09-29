package com.example.demo.Service;

import java.util.List;

import com.example.demo.dtos.EvaluationResult;

public interface AiClient {
	List<String> generateQuestions(String role, String difficulty, int count);
    EvaluationResult evaluateAnswer(String role, String difficulty, String question, String answer);
}
