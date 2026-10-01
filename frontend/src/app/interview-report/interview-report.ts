import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InterviewService } from '../services/interview-service';
import { CommonModule } from '@angular/common';

@Component({
  imports: [CommonModule],
  selector: 'app-interview-report',
  styleUrl: './interview-report.css',
  templateUrl: './interview-report.html',
})
export class InterviewReport implements OnInit {
    interviewId!: number;
  report: any = null;

  loading = true;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private interviewService: InterviewService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.interviewId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadOrGenerateReport();
  }

  private loadOrGenerateReport(): void {
    this.loading = true;
    this.errorMessage = '';

    this.interviewService.getReport(this.interviewId).subscribe(
      (existing) => {
        this.report = existing;
        this.loading = false;
        this.cdr.detectChanges();
      },
      (error) => {
        if (error.status === 404) {
          this.generateReport();
        } else {
          this.errorMessage = 'Could not load the report. Please try again.';
          this.loading = false;
          this.cdr.detectChanges();
        }
      }
    );
  }

  private generateReport(): void {
    this.interviewService.generateReport(this.interviewId).subscribe(
      (response) => {
        this.report = response;
        this.loading = false;
        this.cdr.detectChanges();
      },
      (error) => {
        console.log(error);
        this.errorMessage = error.status === 400
          ? 'Please answer all questions before viewing the report.'
          : 'Could not generate the report. Please try again.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    );
  }

  backToQuestions(): void {
    this.router.navigate(['/interview-setup', this.interviewId]);
  }

  goToDashboard(): void {
    this.router.navigate(['interview-setup']); // adjust to your actual route
  }
}
