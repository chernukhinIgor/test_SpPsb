import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Params} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { Task} from '../classes'
const route = './app/task/';

@Component({
    selector: 'task-comp',
    templateUrl: route + 'task.html',
    providers: [HttpService]
})

export class TaskComponent implements OnInit {

    task: Task;
    condition: boolean=false;

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService){}

    ngOnInit(){
        let options: URLSearchParams = new URLSearchParams();

        this.activatedRoute.params.subscribe((params: Params) => {
            options.set('id', params['id']);
        });

        this.httpService.getData('tasks.json', options).subscribe((data: Response) => this.task=data.json().data[0]);
    }

    toggle(){
        this.condition=true;
    }
}