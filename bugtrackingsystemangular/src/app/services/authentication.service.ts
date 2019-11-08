import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

export class User{
  constructor(
    public status:string,
     ) {}
  
}

@Injectable({
  providedIn: 'root'
})
/**
	 *author: Venkatesh
	 *Description : This class authenticates the user if a valid JWT token is present  
	 *created Date: 23/10/2019
	 *last modified : 23/10/2019            
	 */
export class AuthenticationService {

  user:any={}
  role:string

  constructor(
    private httpClient:HttpClient,private router: Router
  ) { 
     }
     //this function gets the username and passord from component and connects 
     //to the database for if credentials are valid then JWT token is retreived.
     authenticate(username, password) {
      return this.httpClient.post<any>('http://localhost:9050/authenticate',{username,password})
     }
    getUserRole(loginName){
      return this.httpClient.get<any>('http://localhost:9050/getRole?loginName='+loginName)
    }

}
