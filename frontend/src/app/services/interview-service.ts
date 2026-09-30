import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Observable } from 'rxjs';

const BASE_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})

export class InterviewService {
constructor(private http: HttpClient) {}

  createInterview(data: any): Observable<any> {
    return this.http.post(BASE_URL + "api/interview", data);
  }

  getInterviewsByUser(userId: number): Observable<any> {
    return this.http.get(BASE_URL + `api/interview/user/${userId}`);
  }
  generateQuestions(interviewId: number): Observable<any> {
    return this.http.post(BASE_URL + `api/interviews/${interviewId}/questions/generate`, {});
  }

  getQuestions(interviewId: number): Observable<any> {
    return this.http.get(BASE_URL + `api/interviews/${interviewId}/questions`);
  }

  submitAnswer(questionId: number, answer: string): Observable<any> {
    return this.http.post(BASE_URL + `api/questions/${questionId}/answer`, { answer });
  }
}
