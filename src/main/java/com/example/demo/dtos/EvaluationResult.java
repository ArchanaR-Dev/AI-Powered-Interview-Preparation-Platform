package com.example.demo.dtos;

public class EvaluationResult {
	private String technicalFeedback;
    private String communicationFeedback;
    private String missingPoints;
    private Double score;

    public EvaluationResult(String technicalFeedback, String communicationFeedback,
                            String missingPoints, Double score) {
        this.technicalFeedback = technicalFeedback;
        this.communicationFeedback = communicationFeedback;
        this.missingPoints = missingPoints;
        this.score = score;
    }

    public String getTechnicalFeedback() { return technicalFeedback; }
    public String getCommunicationFeedback() { return communicationFeedback; }
    public String getMissingPoints() { return missingPoints; }
    public Double getScore() { return score; }
}
