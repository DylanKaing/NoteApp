import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth-service';

export const authGuard: CanActivateFn = (route, state) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  //If logged in allow access to route
  if (authService.isLoggedIn()) {
    return true;
  }

  //If not logged in redirect to login page
  router.navigate(['/login']);
  return false;
};
