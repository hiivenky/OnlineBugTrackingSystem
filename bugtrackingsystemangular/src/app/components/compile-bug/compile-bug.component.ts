import { Component, OnInit } from '@angular/core';
import { DeveloperService } from 'src/app/services/developer.service';

@Component({
  selector: 'app-compile-bug',
  templateUrl: './compile-bug.component.html',
  styleUrls: ['./compile-bug.component.css']
})
export class CompileBugComponent implements OnInit {

  errors:any=[];
  model:any={}
  assigned=true;
  finalCodeSnippet='';

  constructor(private developerService:DeveloperService) {
    developerService.getTicket().subscribe(
      (data)=>{
        alert(data);
        if(data==null){
          this.model.code = "No ticket Assigned";
        }
        else{
          this.model.code = data["token"];
          this.assigned=false;
        }
      },
      (error)=>{
        alert(error.error);
      }
    )
  }

  ngOnInit() {
  }

  compile(){
    this.developerService.compile(this.model).subscribe(
      (data)=>{
        this.errors=data;
        if(this.errors[0]==='Compilation sucessfull'){
          this.finalCodeSnippet=this.model.code;
          this.assigned=true;
          alert(this.finalCodeSnippet);
          this.submit();
        }
      },
      (error)=>{
        alert(error.error);
      }
    )
  }
  submit(){
    this.developerService.submit(this.finalCodeSnippet).subscribe(
      (data)=>{
        alert(data);
        window.location.reload();
      },
      (error)=>{
        alert(error);
      }
    )
  }

}
