import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-raise-ticket',
  templateUrl: './raise-ticket.component.html',
  styleUrls: ['./raise-ticket.component.css']
})
export class RaiseTicketComponent implements OnInit {

  model:any={}

  constructor(private managerService:ManagerService,private router:Router) { }

  ngOnInit() {
  }

  raiseTicket(data,bug,dev){
    alert(data+" "+bug+" "+dev);
    this.managerService.raiseTicket(this.model,data,bug,dev).subscribe(
      (data)=>{
        alert(data["response"]);
      },
      (error)=>{
        alert(error.error)
      }
    )
  }

}
