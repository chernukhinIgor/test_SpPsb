import { Component, OnInit} from '@angular/core';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { Task} from '../classes'
import { Title } from '@angular/platform-browser';
const route = './app/tasks/';


@Component({
    selector: 'tasks-comp',
    templateUrl: route + 'tasks.html',
    providers: [HttpService]
})

export class TasksComponent implements OnInit {

    tasks: Task[];
    condition: boolean=false;

    constructor(private httpService: HttpService){}

    ngOnInit(){
        let title = new Title('');
        title.setTitle('View Tasks');
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData('tasks.json', options).subscribe((data: Response) => this.tasks=data.json().data);
    }

    toggle(){
        this.condition=true;
    }
}