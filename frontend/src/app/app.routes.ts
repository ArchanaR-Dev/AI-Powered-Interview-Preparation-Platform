import { Routes } from '@angular/router';
import { LogIn } from './pages/log-in/log-in';
import { Register  } from './pages/register/register';

export const routes: Routes = [
    { path: '', component: LogIn },
    { path: 'register', component: Register }
];
