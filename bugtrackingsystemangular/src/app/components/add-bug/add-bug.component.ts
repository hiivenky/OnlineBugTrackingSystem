import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-add-bug',
  templateUrl: './add-bug.component.html',
  styleUrls: ['./add-bug.component.css']
})
export class AddBugComponent implements OnInit {

  model:any={}
  data2=2;

  constructor(private managerService:ManagerService) { 
  }

  ngOnInit() {
  }

  addBug(){
    this.managerService.addBug(this.model,this.data2).subscribe(
      (data)=>{
        alert(data["response"]);
      },
      (error)=>{
        alert(error.error)
      }
    )
  }

}
