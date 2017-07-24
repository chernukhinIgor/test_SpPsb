import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { Routes, RouterModule } from '@angular/router';
import { HttpModule }   from '@angular/http';
import { AppComponent }   from './app.component';
import { HomeComponent }   from './home/home.component';
import { Edit }   from './edit/edit';
import { NotFoundComponent }   from './not-found.component';
import { TaskComponent }   from './task/task';
import { TasksComponent }   from './tasks/tasks';
import { FormsModule }   from '@angular/forms';



// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent },
    { path: 'task/edit/:id', component: Edit },
    { path: 'task/edit', component: Edit },
    { path: 'task/get/:id', component: TaskComponent },
    { path: 'tasks', component: TasksComponent },
    { path: '**', component: NotFoundComponent }
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes), HttpModule, FormsModule],
    declarations: [ AppComponent, HomeComponent, Edit, TasksComponent, TaskComponent, NotFoundComponent],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }