import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Response} from '@angular/http';
import {HttpService} from '../http.service';
import {Title} from '@angular/platform-browser';
import {User, Task} from '../classes'

const route = './app/profile/';

@Component({
    selector: 'profile-comp',
    templateUrl: route + 'profile.html',
    providers: [HttpService]
})

export class ProfileComponent implements OnInit {

    user: User;
    tasks: Task[];

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService) {
    }

    ngOnInit() {
        let title = new Title('');
        title.setTitle('View Profile');

        let options: URLSearchParams = new URLSearchParams();

        this.activatedRoute.params.subscribe((params: Params) => {
            options.set('id', params['id']);
            //this.httpService.getData( 'user/' + params['id'], null).subscribe((data: Response) => {
            this.httpService.getData( 'user.json', null).subscribe((data: Response) => {
                if (data.json().success == true) {
                    this.user = data.json().data[0];
                } else {
                    alert(data.json().message);
                }
            });

            this.httpService.getData('tasks.json', null).subscribe((data: Response) => {
                //this.httpService.getData('userCreatedTasks/' + params['id'], null).subscribe((data: Response) => {
                if (data.json().success == true) {
                    this.tasks = data.json().data;
                } else {
                    alert(data.json().message);
                }
            });

        });
    }
}