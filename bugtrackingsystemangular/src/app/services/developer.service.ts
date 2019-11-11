import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DeveloperService {
  headers:any

  constructor(private myhttp: HttpClient) {
    this.headers = new HttpHeaders().set("Authorization", sessionStorage.getItem("token"));
  }

  compile(data:any){
    let form = new FormData();
    //  alert("inside service"+startDate)
    form.append("code", data.code);
    return this.myhttp.post("http://localhost:9050/compile", form, { headers: this.headers });
  }
  getTicket(){
    return this.myhttp.get("http://localhost:9050/getTicket",{ headers: this.headers });
  }
  submit(finalCodeSnippet){
    let form = new FormData();
    //  alert("inside service"+startDate)
    alert("submitting "+finalCodeSnippet)
    form.append("finalCode", finalCodeSnippet);
    return this.myhttp.post("http://localhost:9050/submit",form,{ headers: this.headers });
  }
}
