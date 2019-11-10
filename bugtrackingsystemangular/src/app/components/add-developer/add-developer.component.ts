import { Component, OnInit } from '@angular/core';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-add-developer',
  templateUrl: './add-developer.component.html',
  styleUrls: ['./add-developer.component.css']
})
export class AddDeveloperComponent implements OnInit {

  model:any={};
    confirmpassword:string='';
    buttondisable=true;
    nameStatus=false;
    phoneNoStatus=false
    passwordStatus=false
    registrationStatus:string='';

  constructor(private managerService:ManagerService) { }

  ngOnInit() {
  }

  addDeveloper(){
    this.managerService.addDeveloper(this.model).subscribe(
      (data)=>{
        alert("added successfully")
      },
      (error)=>{
        alert(error.error);
      }
    )
  }

  validate(){
        
    if(this.model.userName!=''&&this.model.userName.match("[a-zA-Z\\s]")&&this.model.userName.length>=4){
        console.log("entered")
        this.nameStatus=true;
    }
    else{
        console.log("false block")
        this.nameStatus=false;
        return true
    }
    if(this.model.userPassword!=''&& this.model.userPassword.length>=8){
        console.log("inside password validation")
        this.passwordStatus=true
    }
    else{
        console.log("inside password validation false")
        this.passwordStatus=false
        return true;
    }
    if(this.model.phoneNo!=''&&this.model.phoneNo.match("\\d{10}")){
        console.log("inside phoneNumber validation")
        this.phoneNoStatus=true
    }
    else{
        this.phoneNoStatus=false
        return true;
    }
    if(this.confirmpassword==''||this.confirmpassword!=this.model.userPassword){
        console.log("inside confirmpassword validation")
        return true;
    }
    return false;
}

}
