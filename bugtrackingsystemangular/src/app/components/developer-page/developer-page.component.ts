import { Component, OnInit } from '@angular/core';
import { DeveloperService } from 'src/app/services/developer.service';

@Component({
  selector: 'app-developer-page',
  templateUrl: './developer-page.component.html',
  styleUrls: ['./developer-page.component.css']
})
export class DeveloperPageComponent implements OnInit {

  model:any={}

  loginName='';

  compilerToggle=true;

  compileBug=false;

  constructor(private developerService:DeveloperService) { }

  ngOnInit() {
    this.loginName=sessionStorage.getItem('username');
  }

  compileButtonToggle(){
    this.compileBug=false;
    this.compilerToggle=!this.compilerToggle;
  }

  activateCompileProject(){
    alert("inside activate")
    this.compileBug=true;
  }

  compile(){
    this.developerService.compile(this.model).subscribe(
      (data)=>{
        alert(data)
      },
      (error)=>{
        alert(error.error);
      }
    )
  }



}
