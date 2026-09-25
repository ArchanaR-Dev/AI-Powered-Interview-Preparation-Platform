package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Interview;
import com.example.demo.Service.InterviewService;
import com.example.demo.dtos.InterviewRequest;
import com.example.demo.dtos.InterviewResponse;

@RestController
@RequestMapping("/api/interview")
//@CrossOrigin("*")
public class InterviewController {
	    private InterviewService interviewService;
	    
	    public InterviewController(InterviewService interviewService) {
	    	this.interviewService=interviewService;
	    }

	    @PostMapping
	    public ResponseEntity<?> createInterview(@RequestBody InterviewRequest request) {
	        if (request.getRole() == null || request.getRole().isBlank()
	                || request.getDifficulty() == null || request.getDifficulty().isBlank()
	                || request.getUserId() == null) {
	            return new ResponseEntity<>("Role, difficulty and userId are required", HttpStatus.BAD_REQUEST);
	        }

	        Interview created = interviewService.createInterview(request);
	        if (created == null) {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }

	        return new ResponseEntity<>(new InterviewResponse(created), HttpStatus.CREATED);
	    }

	    @GetMapping("/user/{userId}")
	    public ResponseEntity<?> getInterviewsByUser(@PathVariable Long userId) {
	        List<InterviewResponse> interviews = interviewService.getInterviewsByUser(userId)
	                .stream()
	                .map(InterviewResponse::new)
	                .collect(Collectors.toList());
	        return new ResponseEntity<>(interviews, HttpStatus.OK);
	    }
}
