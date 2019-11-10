import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-add-bug',
  templateUrl: './add-bug.component.html',
  styleUrls: ['./add-bug.component.css']
})
export class AddBugComponent implements OnInit {

  model:any={}

  constructor(private managerService:ManagerService) { 
  }

  ngOnInit() {
  }

  addBug(){
    this.managerService.addBug(this.model).subscribe(
      (data)=>{
        alert(data);
      },
      (error)=>{
        alert(error.error)
      }
    )
  }

}
