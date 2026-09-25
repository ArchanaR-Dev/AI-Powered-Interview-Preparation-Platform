import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../services/auth';
import { Router, RouterLink } from '@angular/router';

@Component({
  imports: [FormsModule, CommonModule,RouterLink],
  selector: 'app-log-in',
  styleUrl: './log-in.css',
  templateUrl: './log-in.html',
})
export class LogIn {
    user={
          email:'',
          password:''
        };
    
        message='';
    
        constructor(private auth:Auth,  private router: Router ){}
        loginUser(){
          this.auth.login(this.user).subscribe(
            (response)=>{
              console.log(response);
              this.message="User registered successfully!";
               this.router.navigate(['/dashboard']);
            },
            (error)=>{
              console.log(error);
              this.message="Error occurred during registration.";
            }
          );
    
        }
}
