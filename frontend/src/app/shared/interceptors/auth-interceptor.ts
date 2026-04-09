import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  
  //Get token from the localStorage
  const token = localStorage.getItem('token');

  //If token exists, clone request and add auth header
  if (token) {
    const clonedReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(clonedReq);
  }


  //If no token, pass the request as is
  return next(req);
};
