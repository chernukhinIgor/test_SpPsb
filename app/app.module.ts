import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { Routes, RouterModule } from '@angular/router';
import { HttpModule }   from '@angular/http';
import { AppComponent }   from './app.component';
import { HomeComponent }   from './home/home.component';
import { NotFoundComponent }   from './not-found.component';
import { TaskComponent }   from './task/task';
import { TasksComponent }   from './tasks/tasks';



// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent },
    { path: 'task/get/:id', component: TaskComponent },
    { path: 'tasks', component: TasksComponent },
    { path: '**', component: NotFoundComponent }
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes), HttpModule],
    declarations: [ AppComponent, HomeComponent, TasksComponent, TaskComponent, NotFoundComponent],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }