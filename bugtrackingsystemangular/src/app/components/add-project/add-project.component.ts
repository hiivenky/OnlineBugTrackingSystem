import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {

    model:any={};
    projCostStatus=false;
    buttondisable=true;
    nameStatus=false;
    
    registrationStatus:string='';

  constructor(private managerService:ManagerService) { }

  ngOnInit() {
  }

  addProject(startDate){
    this.managerService.addProject(this.model,startDate).subscribe(
      (data)=>{
        alert(data);
      },
      (error)=>{
        alert(error.error)
      }
    )
  }

  validate(){
    if(this.model.projName!=''&&this.model.projName.match("[a-zA-Z\\s]")&&this.model.projName.length>=4){
      this.nameStatus=true;
    }
    else{
      console.log("false block")
      this.nameStatus=false;
      return true
    }
    if(this.model.projCost>=1000){
      this.projCostStatus=true;
    }
    else{
      console.log("false block")
      this.projCostStatus=false;
      return true
    }
    return false;
  }

}
