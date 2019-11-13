import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';
import { saveAs } from 'file-saver';
@Component({
  selector: 'app-view-bugs',
  templateUrl: './view-bugs.component.html',
  styleUrls: ['./view-bugs.component.css']
})
export class ViewBugsComponent implements OnInit {

  model:any=[]
  bugs:any=[]

  constructor(private managerService:ManagerService) { 
    this.managerService.getBugs().subscribe(
      (data)=>{
        this.bugs=data
      },
      (error)=>{
        alert(error.error);
      }
    )
  }

  ngOnInit() {
  }

  activateAddBug(data){
    this.managerService.getPdf(data).subscribe(
      (response:any) => {
        var blob = new Blob([response], {type: 'application/pdf'});
        var filename = 'FinalCode.pdf';
        saveAs(blob,filename);  
      }
    )
  }

}
