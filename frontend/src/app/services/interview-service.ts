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
    return this.http.post(BASE_URL + "api/interviews", data);
  }

  getInterviewsByUser(userId: number): Observable<any> {
    return this.http.get(BASE_URL + `api/interviews/user/${userId}`);
  }
}
