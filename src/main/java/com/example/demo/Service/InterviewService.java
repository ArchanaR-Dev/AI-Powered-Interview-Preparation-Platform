package com.example.demo.Service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Interview;
import com.example.demo.Entity.User;
import com.example.demo.dtos.InterviewRequest;
import com.example.demo.repository.InterviewRepository;
import com.example.demo.repository.UserRepository;

@Service
public class InterviewService {
		
	private InterviewRepository interviewRepository;
	private UserRepository userRepository;
	
	public InterviewService(InterviewRepository interviewRepository,UserRepository userRepository) {
		this.interviewRepository=interviewRepository;
		this.userRepository=userRepository;
	}
	

    public Interview createInterview(InterviewRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            return null; // controller will treat as "user not found"
        }

        Interview interview = new Interview();
        interview.setRole(request.getRole());
        interview.setDifficulty(request.getDifficulty());
        interview.setUser(userOpt.get());

        return interviewRepository.save(interview);
    }

    public List<Interview> getInterviewsByUser(Long userId) {
        return interviewRepository.findByUserId(userId);
    }

    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }
}
