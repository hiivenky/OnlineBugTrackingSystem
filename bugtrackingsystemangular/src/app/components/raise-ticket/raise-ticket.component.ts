import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-raise-ticket',
  templateUrl: './raise-ticket.component.html',
  styleUrls: ['./raise-ticket.component.css']
})
export class RaiseTicketComponent implements OnInit {

  model:any={}

  constructor(private managerService:ManagerService) { }

  ngOnInit() {
  }

  raiseTicket(data,bug,dev){
    alert(data+" "+bug+" "+dev);
    this.managerService.raiseTicket(this.model,data,bug,dev).subscribe(
      (data)=>{
        alert(data);
      },
      (error)=>{
        alert(error.error)
      }
    )
  }

}
