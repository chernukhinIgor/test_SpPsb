import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { Routes, RouterModule, CanActivate } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpModule }   from '@angular/http';
import { LoginComponent }   from './login/login';
import { RegisterComponent }   from './register/register';
import { AppComponent }   from './app.component';
import { HomeComponent }   from './home/home.component';
import { Edit }   from './edit/edit';
import { UserEdit }   from './user_edit/user_edit';
import { NotFoundComponent }   from './not-found.component';
import { TaskComponent }   from './task/task';
import { TasksComponent }   from './tasks/tasks';
import { FormsModule }   from '@angular/forms';
import { UserComponent }   from './user/user';
import { ProfileComponent }   from './profile/profile';
import { UsersComponent }   from './users/users';
import { myNavbar }   from './navbar/navbar';
import { Animate }   from './notify/index';
import { DeleteDirective }   from './delete.directive';
import { PagerService } from './pagination_service/index';
import { AuthGuard } from './_guards/index';
import { AuthenticationService, UserService } from './_services/index';
import { ModalDirective } from './modal.directive';
import { ForgotPasswordComponent } from "./forgot_password/forgot_password";

import * as _ from 'underscore';

// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent},
    { path: 'register', component: RegisterComponent },
    { path: 'forgotpassword', component: ForgotPasswordComponent },
    { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
    { path: 'task/edit/:id', component: Edit, canActivate: [AuthGuard] },
    { path: 'task/edit', component: Edit, canActivate: [AuthGuard] },
    { path: 'task/get/:id', component: TaskComponent, canActivate: [AuthGuard] },
    { path: 'tasks', component: TasksComponent, canActivate: [AuthGuard] },
    { path: 'user/edit/:id', component: UserEdit, canActivate: [AuthGuard] },
    { path: 'user/edit', component: UserEdit, canActivate: [AuthGuard] },
    { path: 'user/get/:id', component: UserComponent, canActivate: [AuthGuard] },
    { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
    { path: '**', component: NotFoundComponent, canActivate: [AuthGuard] }
];

@NgModule({
    imports: [
        BrowserModule,
        RouterModule.forRoot(appRoutes),
        BrowserAnimationsModule,
        HttpModule,
        FormsModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        Edit,
        UserEdit,
        UsersComponent,
        UserComponent,
        TasksComponent,
        TaskComponent,
        NotFoundComponent,
        DeleteDirective,
        LoginComponent,
        RegisterComponent,
        myNavbar,
        Animate,
        ModalDirective,
        ForgotPasswordComponent,
        ProfileComponent
    ],
    providers: [
        PagerService,
        AuthGuard,
        AuthenticationService,
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule { }