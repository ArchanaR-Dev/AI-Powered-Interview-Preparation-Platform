package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.InterviewQuestion;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion,Long>{

	List<InterviewQuestion> findByInterviewIdOrderByQuestionNumberAsc(Long interviewId);

}
