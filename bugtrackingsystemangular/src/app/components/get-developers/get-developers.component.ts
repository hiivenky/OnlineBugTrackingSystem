import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-get-developers',
  templateUrl: './get-developers.component.html',
  styleUrls: ['./get-developers.component.css']
})
export class GetDevelopersComponent implements OnInit {

  developers:any=[];

  constructor(private managerService:ManagerService) {
    this.managerService.getDevelopers().subscribe(
      (data)=>{
        this.developers=data;
      },
      (error)=>{
        alert(error.error)
      }
    )
  }

  ngOnInit() {
  }

  getDevelopers(){
    this.managerService.getDevelopers()
  }

 

}
