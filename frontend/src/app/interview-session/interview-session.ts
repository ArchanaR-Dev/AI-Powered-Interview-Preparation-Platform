import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InterviewService } from '../services/interview-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [CommonModule, FormsModule],
  selector: 'app-interview-session',
  styleUrl: './interview-session.css',
  templateUrl: './interview-session.html',
})
export class InterviewSession implements OnInit {
  interviewId!: number;
  questions: any[] = [];
  currentIndex = 0;
  answerText = '';

  loadingQuestions = true;
  submitting = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private interviewService: InterviewService,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit(): void {
    this.interviewId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadOrGenerateQuestions();
  }

  get currentQuestion() {
    return this.questions[this.currentIndex];
  }

  get isLastQuestion(): boolean {
    return this.currentIndex === this.questions.length - 1;
  }

  get answeredCount(): number {
    return this.questions.filter(q => q.userAnswer).length;
  }

  private loadOrGenerateQuestions(): void {
    this.loadingQuestions = true;
    this.errorMessage = '';

    this.interviewService.getQuestions(this.interviewId).subscribe(
      (existing) => {
        if (existing && existing.length > 0) {
          this.questions = existing;
          this.jumpToFirstUnanswered();
          this.loadingQuestions = false;
          this.cdr.detectChanges();
        } else {
          this.generateQuestions();
        }
      },
      () => this.generateQuestions()
    );
  }

  private generateQuestions(): void {
    this.interviewService.generateQuestions(this.interviewId).subscribe(
      (response) => {
        this.questions = response;
        this.loadingQuestions = false;
        this.cdr.detectChanges();
      },
      (error) => {
        console.log(error);
        this.errorMessage = 'Could not generate questions. Please try again.';
        this.loadingQuestions = false;
        this.cdr.detectChanges();
      }
    );
  }

  private jumpToFirstUnanswered(): void {
    const firstUnanswered = this.questions.findIndex(q => !q.userAnswer);
    this.currentIndex = firstUnanswered === -1 ? 0 : firstUnanswered;
  }

  submitAnswer(): void {
    if (!this.answerText.trim()) {
      this.errorMessage = 'Please write an answer before submitting.';
      return;
    }

    this.submitting = true;
    this.errorMessage = '';

    this.interviewService.submitAnswer(this.currentQuestion.id, this.answerText).subscribe(
      (updated) => {
        this.questions[this.currentIndex] = updated;
        this.submitting = false;
        this.answerText = '';
        this.cdr.detectChanges();
      },
      (error) => {
        console.log(error);
        this.errorMessage = 'Failed to submit answer. Please try again.';
        this.submitting = false;
        this.cdr.detectChanges();
      }
    );
  }

  nextQuestion(): void {
    if (!this.isLastQuestion) {
      this.currentIndex++;
      this.answerText = '';
      this.errorMessage = '';
    }
  }

  previousQuestion(): void {
    if (this.currentIndex > 0) {
      this.currentIndex--;
      this.answerText = '';
      this.errorMessage = '';
    }
  }

  finishInterview(): void {
    this.router.navigate(['/interview-setup', this.interviewId, 'report']);// adjust to your actual post-interview route
  }
}
