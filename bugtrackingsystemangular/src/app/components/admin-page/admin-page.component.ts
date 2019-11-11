import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  
  loginName:String =''
  addDeveloper=false
  addProject=false
  addBug=false
  viewProjects=false
  projectToggle=true;
  getDevelopers=false;
  raiseTicket=false;
  developerToggle=true;

  constructor() { 
  }

  ngOnInit() {
    this.loginName = sessionStorage.getItem("username");
  }
  activateAddDeveloper(){
    this.raiseTicket=false;
    this.viewProjects=false;
    this.addProject=false;
    this.addDeveloper=true;
    this.addBug=false;
    this.getDevelopers=false;
  }
  activateAddProject(){
    this.raiseTicket=false;
    this.viewProjects=false;
    this.addDeveloper=false;
    this.addProject=true;
    this.addBug=false;
    this.getDevelopers=false;
  }
  activateAddBug(){
    this.raiseTicket=false;
    this.viewProjects=false;
    this.addDeveloper=false;
    this.addProject=false;
    this.addBug=true;
    this.getDevelopers=false;
  }
  projectButtonToggle(){
    this.raiseTicket=false;
    this.viewProjects=false;
    this.addProject=false;
    this.addBug=false;
    this.addDeveloper=false;
    this.getDevelopers=false;
    this.projectToggle=!this.projectToggle;
  }
  activateViewProjects(){
    this.raiseTicket=false;
    this.viewProjects=true;
    this.addProject=false;
    this.addBug=false;
    this.addDeveloper=false; 
    this.getDevelopers=false; 
  }

  activateGetDevelopers(){
    this.raiseTicket=false;
    this.viewProjects=false;
    this.addProject=false;
    this.addBug=false;
    this.addDeveloper=false; 
    this.getDevelopers=true;

  }
  developerButtonToggle(){
    this.raiseTicket=false;
    this.viewProjects=false;
    this.addProject=false;
    this.addBug=false;
    this.addDeveloper=false;
    this.getDevelopers=false;
    this.developerToggle=!this.developerToggle;
  }
  activateRaiseTicket(){
    this.raiseTicket=true;
    this.viewProjects=false;
    this.addProject=false;
    this.addBug=false;
    this.addDeveloper=false;
    this.getDevelopers=false;
  }

}
