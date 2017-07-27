import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Response} from '@angular/http';
import {HttpService} from '../http.service';
import {User, Task} from '../classes'
import {Title} from '@angular/platform-browser';

const route = './app/user/';

@Component({
    selector: 'user-comp',
    templateUrl: route + 'user.html',
    providers: [HttpService]
})

export class UserComponent implements OnInit {

    user: User;
    tasks: Task[];

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService) {
    }

    ngOnInit() {
        let title = new Title('');
        title.setTitle('View User');
        let options: URLSearchParams = new URLSearchParams();

        this.activatedRoute.params.subscribe((params: Params) => {
            options.set('id', params['id']);
            this.httpService.getData( 'user/' + params['id'], null).subscribe((data: Response) => {
                if (data.json().success == true) {
                    this.user = data.json().data[0];
                } else {
                    alert(data.json().message)
                }
            });

            this.httpService.getData('userCreatedTasks/' + params['id'], null).subscribe((data: Response) => {
                if (data.json().success == true) {
                    this.tasks = data.json().data;
                } else {
                    alert(data.json().message)
                }
            });

        });

    }

}