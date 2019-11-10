import { Component, OnInit } from '@angular/core';
import { DeveloperService } from 'src/app/services/developer.service';

@Component({
  selector: 'app-compile-bug',
  templateUrl: './compile-bug.component.html',
  styleUrls: ['./compile-bug.component.css']
})
export class CompileBugComponent implements OnInit {
  model:any={}

  constructor(private developerService:DeveloperService) { }

  ngOnInit() {
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
