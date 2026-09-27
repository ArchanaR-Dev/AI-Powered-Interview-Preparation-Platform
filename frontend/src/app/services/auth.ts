import { HttpClient } from '@angular/common/http';
import { Injectable} from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

const BASIC_URL="http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class Auth {

constructor(private http:HttpClient, private router: Router) { }

  register(data: any):Observable<any>{
  return this.http.post(BASIC_URL +"api/auth/sign-up", data);
  }

  login(user:any):Observable<any>{
    return this.http.post(BASIC_URL +"api/auth/log-in", user).pipe(
      tap((response: any) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('user', JSON.stringify(response));
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUser(): any {
    const stored = localStorage.getItem('user');
    return stored ? JSON.parse(stored) : null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
  
}


