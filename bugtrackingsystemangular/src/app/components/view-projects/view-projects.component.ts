import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-view-projects',
  templateUrl: './view-projects.component.html',
  styleUrls: ['./view-projects.component.css']
})
export class ViewProjectsComponent implements OnInit {

  projects:any=[];
  addBug=true;

  constructor(private managerService:ManagerService) { 
    this.managerService.viewProjects().subscribe(
      (data)=>{
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
    this.addBug=false;
  }
  addBugToProject(data1,data2){
    alert(data1)
    this.managerService.addBug(data1,data2).subscribe(
      (data)=>{
        alert(data);
      },
      (error)=>{
        alert(error.error)
      }
    )
  }

}
