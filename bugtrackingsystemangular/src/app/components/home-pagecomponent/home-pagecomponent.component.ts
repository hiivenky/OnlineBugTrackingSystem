import { Component, OnInit, Input } from '@angular/core';
import { ThemePalette } from '@angular/material/core';


@Component({
  selector: 'app-home-pagecomponent',
  templateUrl: './home-pagecomponent.component.html',
  styleUrls: ['./home-pagecomponent.component.css']
})
export class HomePagecomponentComponent implements OnInit {

  @Input()
  color: ThemePalette

  constructor() { }

  ngOnInit() {
  }

}
