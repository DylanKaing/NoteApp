import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../shared/services/auth-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'

@Component({
  selector: 'app-login',
  imports: [    
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    FormsModule,
    CommonModule,
    RouterLink
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  //vars for the inputs
  username = '';
  password = '';

  //services
  constructor(private authService: AuthService, private router: Router){}

  errorMessage = '';

  //Login method after button is clicked
  login(){
    this.authService.login(this.username, this.password).subscribe({
      next: (token) => {
        this.authService.storeToken(token);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Login failed', err);
        this.errorMessage = 'Invalid username or password';
      }
    });
  }

}
