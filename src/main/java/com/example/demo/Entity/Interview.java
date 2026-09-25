package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="interview")
public class Interview {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String role;
	    private String difficulty;
	    
	    @ManyToOne
	    @JoinColumn(name="user_id")
	    private User user;
	    
	    public Interview() {}

		public Interview(Long id, String role, String difficulty, User user) {
			super();
			this.id = id;
			this.role = role;
			this.difficulty = difficulty;
			this.user = user;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getDifficulty() {
			return difficulty;
		}

		public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		};
	    
	    

	
}
