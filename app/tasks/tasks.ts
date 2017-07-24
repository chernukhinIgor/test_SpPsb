import { Component, OnInit} from '@angular/core';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
const route = './app/tasks/';

export class Task{
    task_id: number;
    creator_user_id: number;
    name: string;
    responsible_user_id: number;
    description: string;
}

@Component({
    selector: 'form-app',
    templateUrl: route + 'tasks.html',
    providers: [HttpService]
})

export class TasksComponent implements OnInit {

    tasks: Task[];
    condition: boolean=false;

    constructor(private httpService: HttpService){}

    ngOnInit(){
        this.httpService.getData('task.json').subscribe((data: Response) => this.tasks=data.json().data);
    }

    toggle(){
        this.condition=true;
    }
}