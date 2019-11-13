import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {Routes,RouterModule}  from '@angular/router';
import { MaterialModule } from './material/material.module';
import { AppComponent }  from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from "@angular/forms";
import {MatToolbarModule} from '@angular/material/toolbar';
import { AppRoutingModule } from './/app-routing.module';
import {MatMenuModule} from '@angular/material/menu';
import {MatFormFieldModule} from '@angular/material/form-field';
import { HomePagecomponentComponent } from './components/home-pagecomponent/home-pagecomponent.component'
;
import { LoginPagecomponentComponent } from './components/login-pagecomponent/login-pagecomponent.component'
;
import {MatIconModule} from '@angular/material/icon';
import { RegistrationPageComponent } from './components/registration-page/registration-page.component'
;
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';;
import { AddDeveloperComponent } from './components/add-developer/add-developer.component'
;
import { AddProjectComponent } from './components/add-project/add-project.component'
;
import { AddBugComponent } from './components/add-bug/add-bug.component'
;
import { ViewProjectsComponent } from './components/view-projects/view-projects.component'
;
import { RaiseTicketComponent } from './components/raise-ticket/raise-ticket.component'
;
import { GetDevelopersComponent } from './components/get-developers/get-developers.component'
;
import { DeveloperPageComponent } from './components/developer-page/developer-page.component'
;
import { CompileBugComponent } from './components/compile-bug/compile-bug.component'
;
import { ViewBugsComponent } from './components/view-bugs/view-bugs.component'
;
import { LogOutComponent } from './components/log-out/log-out.component'
const myroute:Routes=[
    {path:'homePage',component:HomePagecomponentComponent},
    {path:'login',component:LoginPagecomponentComponent},
    {path:'registration',component:RegistrationPageComponent},
    {path:'adminPage',component:AdminPageComponent},
    {path:'developerPage',component:DeveloperPageComponent},
    {path:'logOut',component:LogOutComponent},
    { path: '**', redirectTo: '/homePage', pathMatch: 'full' },
]
@NgModule({
    imports: [
        BrowserModule,
        MaterialModule,
        FormsModule,HttpClientModule,
        MatFormFieldModule,
        BrowserAnimationsModule,
        AppRoutingModule,RouterModule.forRoot(myroute),
        MatToolbarModule,
        MatMenuModule,
        MatIconModule,
        MatSidenavModule
        
        ],
    declarations: [
        AppComponent,
        HomePagecomponentComponent	,
        LoginPagecomponentComponent	,
        RegistrationPageComponent,
        AdminPageComponent,
        AddDeveloperComponent,
        AddProjectComponent,
        AddBugComponent,
        ViewProjectsComponent,
        GetDevelopersComponent,
        RaiseTicketComponent,
        DeveloperPageComponent,
        CompileBugComponent,
        ViewBugsComponent,
        LogOutComponent],
    providers: [],
    bootstrap: [AppComponent]
})

export class AppModule { }