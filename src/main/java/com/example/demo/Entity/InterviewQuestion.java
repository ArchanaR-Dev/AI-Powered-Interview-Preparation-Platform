package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "interview_questions")
public class InterviewQuestion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(name = "question_number", nullable = false)
    private Integer questionNumber;

    // Filled in when the user answers
    @Column(name = "user_answer", columnDefinition = "TEXT")
    private String userAnswer;

    // Filled in when the AI evaluates the answer
    @Column(name = "technical_feedback", columnDefinition = "TEXT")
    private String technicalFeedback;

    @Column(name = "communication_feedback", columnDefinition = "TEXT")
    private String communicationFeedback;

    @Column(name = "missing_points", columnDefinition = "TEXT")
    private String missingPoints;

    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    public InterviewQuestion() {}

    public InterviewQuestion(String question, Integer questionNumber, Interview interview) {
        this.question = question;
        this.questionNumber = questionNumber;
        this.interview = interview;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public Integer getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(Integer questionNumber) { this.questionNumber = questionNumber; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }

    public String getTechnicalFeedback() { return technicalFeedback; }
    public void setTechnicalFeedback(String technicalFeedback) { this.technicalFeedback = technicalFeedback; }

    public String getCommunicationFeedback() { return communicationFeedback; }
    public void setCommunicationFeedback(String communicationFeedback) { this.communicationFeedback = communicationFeedback; }

    public String getMissingPoints() { return missingPoints; }
    public void setMissingPoints(String missingPoints) { this.missingPoints = missingPoints; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public Interview getInterview() { return interview; }
    public void setInterview(Interview interview) { this.interview = interview; }
}
