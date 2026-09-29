package com.example.demo.Service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Interview;
import com.example.demo.Entity.InterviewQuestion;
import com.example.demo.dtos.EvaluationResult;
import com.example.demo.repository.InterviewQuestionRepository;
import com.example.demo.repository.InterviewRepository;

@Service
public class InterviewQuestionService {
	    private static final int QUESTION_COUNT = 10;

	    private final InterviewQuestionRepository questionRepository;
	    private final InterviewRepository interviewRepository;
	    private final AiClient aiClient;

	    public InterviewQuestionService(InterviewQuestionRepository questionRepository,
	                                    InterviewRepository interviewRepository,
	                                    AiClient aiClient) {
	        this.questionRepository = questionRepository;
	        this.interviewRepository = interviewRepository;
	        this.aiClient = aiClient;
	    }

	    // Returns null if the interview doesn't exist or doesn't belong to this user
	    @Transactional
	    public List<InterviewQuestion> generateQuestions(Long interviewId, Long userId) {
	        Interview interview = findOwnedInterview(interviewId, userId);
	        if (interview == null) return null;

	        // Don't regenerate if this interview already has questions
	        List<InterviewQuestion> existing =
	                questionRepository.findByInterviewIdOrderByQuestionNumberAsc(interviewId);
	        if (!existing.isEmpty()) return existing;

	        List<String> texts = aiClient.generateQuestions(
	                interview.getRole(), interview.getDifficulty(), QUESTION_COUNT);

	        List<InterviewQuestion> saved = new ArrayList<>();
	        for (int i = 0; i < texts.size(); i++) {
	            saved.add(new InterviewQuestion(texts.get(i), i + 1, interview));
	        }
	        return questionRepository.saveAll(saved);
	    }

	    @Transactional(readOnly = true)
	    public List<InterviewQuestion> getQuestions(Long interviewId, Long userId) {
	        if (findOwnedInterview(interviewId, userId) == null) return null;
	        return questionRepository.findByInterviewIdOrderByQuestionNumberAsc(interviewId);
	    }

	    // Returns null if the question doesn't exist or doesn't belong to this user
	    @Transactional
	    public InterviewQuestion submitAnswer(Long questionId, String answer, Long userId) {
	        InterviewQuestion q = questionRepository.findById(questionId).orElse(null);
	        if (q == null || !q.getInterview().getUser().getId().equals(userId)) return null;

	        EvaluationResult result = aiClient.evaluateAnswer(
	                q.getInterview().getRole(),
	                q.getInterview().getDifficulty(),
	                q.getQuestion(),
	                answer);

	        q.setUserAnswer(answer);
	        q.setTechnicalFeedback(result.getTechnicalFeedback());
	        q.setCommunicationFeedback(result.getCommunicationFeedback());
	        q.setMissingPoints(result.getMissingPoints());
	        q.setScore(result.getScore());

	        return questionRepository.save(q);
	    }

	    private Interview findOwnedInterview(Long interviewId, Long userId) {
	        Interview interview = interviewRepository.findById(interviewId).orElse(null);
	        if (interview == null || !interview.getUser().getId().equals(userId)) return null;
	        return interview;
	    }
}
