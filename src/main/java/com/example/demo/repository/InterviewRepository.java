package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Interview;

@Repository
public interface InterviewRepository extends JpaRepository<Interview,Long> {
	List<Interview> findByUserId(Long userId);
}
