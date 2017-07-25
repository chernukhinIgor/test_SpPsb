import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { Routes, RouterModule } from '@angular/router';
import { HttpModule }   from '@angular/http';
import { AppComponent }   from './app.component';
import { HomeComponent }   from './home/home.component';
import { Edit }   from './edit/edit';
import { UserEdit }   from './user_edit/user_edit';
import { NotFoundComponent }   from './not-found.component';
import { TaskComponent }   from './task/task';
import { TasksComponent }   from './tasks/tasks';
import { FormsModule }   from '@angular/forms';
import { UserComponent }   from './user/user';
import { UsersComponent }   from './users/users';
import { DeleteDirective }   from './delete.directive';


// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent },
    { path: 'task/edit/:id', component: Edit },
    { path: 'task/edit', component: Edit },
    { path: 'task/get/:id', component: TaskComponent },
    { path: 'tasks', component: TasksComponent },
    { path: 'user/edit/:id', component: UserEdit },
    { path: 'user/edit', component: UserEdit },
    { path: 'user/get/:id', component: UserComponent },
    { path: 'users', component: UsersComponent },
    { path: '**', component: NotFoundComponent }
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes), HttpModule, FormsModule],
    declarations: [ AppComponent, HomeComponent, Edit, UserEdit, UsersComponent, UserComponent, TasksComponent, TaskComponent, NotFoundComponent, DeleteDirective],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }