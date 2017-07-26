import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Params} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { Task} from '../classes'
import { Title } from '@angular/platform-browser';
const route = './app/task/';

@Component({
    selector: 'task-comp',
    templateUrl: route + 'task.html',
    providers: [HttpService]
})

export class TaskComponent implements OnInit {

    task: Task;

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService){}

    ngOnInit(){
        let title = new Title('');
        title.setTitle('View Task');
        let options: URLSearchParams = new URLSearchParams();

        this.activatedRoute.params.subscribe((params: Params) => {
            options.set('id', params['id']);
        });

        this.httpService.getData('task/'+options['id'], null).subscribe((data: Response) => {
            if (data.json().success == true) {
                this.task = data.json().data[0];
            } else {
                alert(data.json().message)
            }
        });
    }
}