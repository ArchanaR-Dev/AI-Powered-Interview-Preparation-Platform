import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from '../services/auth';
import { InterviewService } from '../services/interview-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [FormsModule, CommonModule],
  selector: 'app-interview-setup',
  styleUrl: './interview-setup.css',
  templateUrl: './interview-setup.html',
})
export class InterviewSetup {
  roles: string[] = [
    'Frontend Developer',
    'Backend Developer',
    'Full Stack Developer',
  ];

  difficulties: string[] = ['Easy', 'Medium', 'Hard'];

  setup = {
    role: '',
    difficulty: ''
  };

  message = '';
  isError = false;

  constructor(
    private interviewService: InterviewService,
    private auth: Auth,
    private router: Router
  ) {}

  startInterview() {
    const user = this.auth.getUser();

    if (!user) {
      this.message = 'You must be logged in to start an interview.';
      this.isError = true;
      return;
    }

    if (!this.setup.role || !this.setup.difficulty) {
      this.message = 'Please select both role and difficulty.';
      this.isError = true;
      return;
    }

    const payload = {
      role: this.setup.role,
      difficulty: this.setup.difficulty,
      userId: user.id
    };

    this.interviewService.createInterview(payload).subscribe(
      (response) => {
        console.log(response);
        this.message = 'Interview created successfully!';
        this.isError = false;
        // adjust once you have an actual interview session/questions page
        this.router.navigate(['/interview-setup', response.id]);
      },
      (error) => {
        console.log(error);
        this.message = 'Failed to create interview. Please try again.';
        this.isError = true;
      }
    );
  }
}
