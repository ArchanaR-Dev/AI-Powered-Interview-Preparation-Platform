import { Routes } from '@angular/router';
import { LogIn } from './pages/log-in/log-in';
import { Register  } from './pages/register/register';
import { InterviewSetup } from './interview-setup/interview-setup';

export const routes: Routes = [
    { path: '', component: LogIn },
    { path: 'register', component: Register },
    { path:'interview-setup',component:InterviewSetup}
];
