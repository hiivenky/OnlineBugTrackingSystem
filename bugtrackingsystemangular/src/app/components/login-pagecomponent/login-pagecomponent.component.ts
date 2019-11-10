import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-login-pagecomponent',
  templateUrl: './login-pagecomponent.component.html',
  styleUrls: ['./login-pagecomponent.component.css']
})
export class LoginPagecomponentComponent implements OnInit {

  role:{}
    username = ''
    password = ''
    invalidLogin = false
    user:any={}; 

  constructor(private router: Router,private authService:AuthenticationService) { }

  ngOnInit() {
  }

  authenticate(username,password):any{

    this.authService.authenticate(username,password)
    .subscribe
      (
        (data)=>{
          sessionStorage.setItem('username',username);
          let tokenStr='Bearer '+data.token;
          alert(tokenStr);
          sessionStorage.setItem('token',tokenStr);
          this.user=data;
          return data;
        },(error) => {
          alert("Invalid credentials")
          this.router.navigate(['login'])
        }
      ) 
    this.role = this.authService.getUserRole(username).
    subscribe((data)=>{
    this.role=data;
    if(this.role["token"]==='ROLE_ADMIN'){
      this.router.navigate(['adminPage'])
    }
    else if(this.role["token"]==='ROLE_CUSTOMER'){
      this.router.navigate(['developerPage']);
    }
    return data 
    },(error)=>{
      alert(error.error)
    });
}

}
