import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../services/auth';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  imports: [ FormsModule, CommonModule,RouterLink],
  selector: 'app-register',
  styleUrl: './register.css',
  templateUrl: './register.html',
})
export class Register {
    user={
      name:'',
      email:'',
      password:''
    };

    message='';

    constructor(private auth:Auth,  private router: Router){}
    registerUser(){
      this.auth.register(this.user).subscribe(
        (response)=>{
          console.log(response);
          this.message="User registered successfully!";
           this.router.navigate(['/']);
        },
        (error)=>{
          console.log(error);
          this.message="Error occurred during registration.";
        }
      );

    }
}
