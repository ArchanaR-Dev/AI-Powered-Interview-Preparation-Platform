package com.example.demo.dtos;

public class InterviewRequest {
	private String role;
    private String difficulty;
    private Long userId;

    public InterviewRequest() {}

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
