import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {Routes,RouterModule}  from '@angular/router';
import { AppComponent }  from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from "@angular/forms";
import { AppRoutingModule } from './/app-routing.module';
import { HomePagecomponentComponent } from './components/home-pagecomponent/home-pagecomponent.component'
;
import { LoginPagecomponentComponent } from './components/login-pagecomponent/login-pagecomponent.component'
;
import { RegistrationPageComponent } from './components/registration-page/registration-page.component'
const myroute:Routes=[
    {path:'homePage',component:HomePagecomponentComponent},
    {path:'login',component:LoginPagecomponentComponent},
    {path:'registration',component:RegistrationPageComponent},
    { path: '**', redirectTo: '/homePage', pathMatch: 'full' },
]
@NgModule({
    imports: [
        BrowserModule,
        FormsModule,HttpClientModule,
        AppRoutingModule,RouterModule.forRoot(myroute)
        ],
    declarations: [
        AppComponent
,
        HomePagecomponentComponent	,
        LoginPagecomponentComponent	,
        RegistrationPageComponent],
    providers: [ ],
    bootstrap: [AppComponent]
})

export class AppModule { }