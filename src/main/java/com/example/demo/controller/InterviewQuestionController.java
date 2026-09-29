package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.InterviewQuestion;
import com.example.demo.Service.InterviewQuestionService;
import com.example.demo.dtos.AnswerRequest;
import com.example.demo.dtos.QuestionResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class InterviewQuestionController {
	private final InterviewQuestionService questionService;

    public InterviewQuestionController(InterviewQuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/interviews/{interviewId}/questions/generate")
    public ResponseEntity<?> generate(@PathVariable Long interviewId, Authentication auth) {
        List<InterviewQuestion> questions =
                questionService.generateQuestions(interviewId, currentUserId(auth));
        if (questions == null) {
            return new ResponseEntity<>("Interview not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(toResponse(questions), HttpStatus.CREATED);
    }

    @GetMapping("/interviews/{interviewId}/questions")
    public ResponseEntity<?> getQuestions(@PathVariable Long interviewId, Authentication auth) {
        List<InterviewQuestion> questions =
                questionService.getQuestions(interviewId, currentUserId(auth));
        if (questions == null) {
            return new ResponseEntity<>("Interview not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(toResponse(questions), HttpStatus.OK);
    }

    @PostMapping("/questions/{questionId}/answer")
    public ResponseEntity<?> submitAnswer(@PathVariable Long questionId,
                                          @RequestBody AnswerRequest request,
                                          Authentication auth) {
        if (request.getAnswer() == null || request.getAnswer().isBlank()) {
            return new ResponseEntity<>("Answer is required", HttpStatus.BAD_REQUEST);
        }

        InterviewQuestion updated =
                questionService.submitAnswer(questionId, request.getAnswer(), currentUserId(auth));
        if (updated == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new QuestionResponse(updated), HttpStatus.OK);
    }

    private Long currentUserId(Authentication auth) {
        return (Long) auth.getDetails();
    }

    private List<QuestionResponse> toResponse(List<InterviewQuestion> questions) {
        return questions.stream().map(QuestionResponse::new).collect(Collectors.toList());
    }
}
