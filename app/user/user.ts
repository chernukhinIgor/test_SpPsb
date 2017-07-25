import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Params} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { User} from '../classes'
import { Task} from '../classes'
import { Title } from '@angular/platform-browser';
const route = './app/user/';

@Component({
    selector: 'user-comp',
    templateUrl: route + 'user.html',
    providers: [HttpService]
})

export class UserComponent implements OnInit {

    user: User;
    tasks: Task[];

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService){}

    ngOnInit(){
        let title = new Title('');
        title.setTitle('View User');
        let options: URLSearchParams = new URLSearchParams();

        this.activatedRoute.params.subscribe((params: Params) => {
            options.set('id', params['id']);
        });

        this.httpService.getData('user.json', options).subscribe((data: Response) => this.user=data.json().data[0]);
        this.httpService.getData('tasksFromUsers.json', options).subscribe((data: Response) => this.tasks=data.json().data);
    }

}