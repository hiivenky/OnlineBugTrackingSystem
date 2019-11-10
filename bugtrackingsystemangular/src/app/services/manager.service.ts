import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  headers: any

  constructor(private myhttp: HttpClient) {
    this.headers = new HttpHeaders().set("Authorization", sessionStorage.getItem("token"));
  }

  addProject(data: any, startDate) {
    let form = new FormData();
    //  alert("inside service"+startDate)
    form.append("projectName", data.projName);
    form.append("projectCost", data.projCost);
    form.append("endDate", startDate);

    return this.myhttp.post("http://localhost:9050/manager/addProject", form, { headers: this.headers });
  }
  addDeveloper(data: any) {
    let form = new FormData();
    //  alert("inside service"+startDate)
    form.append("employeeName", data.employeeName);
    form.append("userPassword", data.userPassword);
    form.append("emailId", data.emailId);
    form.append("phoneNumber", data.phoneNumber);
    return this.myhttp.post("http://localhost:9050/manager/addDeveloper", form, { headers: this.headers });

  }
  viewProjects() {
    let form = new FormData();
    //  alert("inside service"+startDate)
    return this.myhttp.get("http://localhost:9050/getProjects", { headers: this.headers });
  }

  addBug(data: any) {
    let form = new FormData();
    //  alert("inside service"+startDate)
    form.append("bugDescription", data.bugDescription);
    form.append("projectName", data.projName)
    return this.myhttp.post("http://localhost:9050/manager/addBug", form, { headers: this.headers });
  }

  getDevelopers(){
    return this.myhttp.get("http://localhost:9050/getDevelopers", { headers: this.headers });
  }
} 