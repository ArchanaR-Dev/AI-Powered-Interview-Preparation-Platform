package com.example.demo.dtos;

import com.example.demo.Entity.Interview;

public class InterviewResponse {
	 private Long id;
	    private String role;
	    private String difficulty;
	    private Long userId;
	   

	    public InterviewResponse(Interview interview) {
	        this.id = interview.getId();
	        this.role = interview.getRole();
	        this.difficulty = interview.getDifficulty();
	        this.userId = interview.getUser().getId();
	    }

	    public Long getId() { return id; }
	    public String getRole() { return role; }
	    public String getDifficulty() { return difficulty; }
	    public Long getUserId() { return userId; }
}
