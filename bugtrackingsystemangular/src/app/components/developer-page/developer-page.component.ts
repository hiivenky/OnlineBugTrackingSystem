import { Component, OnInit } from '@angular/core';
import { DeveloperService } from 'src/app/services/developer.service';
import { Router } from '@angular/router';
import { INHERITED_CLASS_WITH_CTOR } from '@angular/core/src/reflection/reflection_capabilities';

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

  constructor(private developerService:DeveloperService,private router:Router) {
  }

  ngOnInit() {
    this.loginName=sessionStorage.getItem('username');
    if(sessionStorage.getItem('username')==null&&sessionStorage.getItem('token')==null){
      alert("problem here")
      this.router.navigate(['login'])
    }
    if(sessionStorage.getItem('role')==='ROLE_ADMIN'){
      alert('inside role admin')
      this.router.navigate(['login'])
    }
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
