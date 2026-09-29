package com.example.demo.dtos;

import com.example.demo.Entity.InterviewQuestion;

public class QuestionResponse {
	private Long id;
    private Integer questionNumber;
    private String question;
    private String userAnswer;
    private String technicalFeedback;
    private String communicationFeedback;
    private String missingPoints;
    private Double score;

    public QuestionResponse(InterviewQuestion q) {
        this.id = q.getId();
        this.questionNumber = q.getQuestionNumber();
        this.question = q.getQuestion();
        this.userAnswer = q.getUserAnswer();
        this.technicalFeedback = q.getTechnicalFeedback();
        this.communicationFeedback = q.getCommunicationFeedback();
        this.missingPoints = q.getMissingPoints();
        this.score = q.getScore();
    }

    public Long getId() { return id; }
    public Integer getQuestionNumber() { return questionNumber; }
    public String getQuestion() { return question; }
    public String getUserAnswer() { return userAnswer; }
    public String getTechnicalFeedback() { return technicalFeedback; }
    public String getCommunicationFeedback() { return communicationFeedback; }
    public String getMissingPoints() { return missingPoints; }
    public Double getScore() { return score; }
}
