import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-view-projects',
  templateUrl: './view-projects.component.html',
  styleUrls: ['./view-projects.component.css']
})
export class ViewProjectsComponent implements OnInit {

  projects:any=[];
  addBug=false;

  constructor(private managerService:ManagerService) { 
    this.managerService.viewProjects().subscribe(
      (data)=>{
        alert(data)
        this.projects=data;
      },
      (error)=>{
        alert(error.error);
      }
    )
  }

  ngOnInit() {
    
  }
  activateAddBug(){
    this.addBug=true;
  }

}
