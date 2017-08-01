import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Response} from '@angular/http';
import {HttpService} from '../http.service';
import {Title} from '@angular/platform-browser';
import {User, Task} from '../classes'
import {Collapse} from '../collapse'
const route = './app/profile/';

@Component({
    selector: 'profile-comp',
    templateUrl: route + 'profile.html',
    providers: [HttpService],
    directives: [Collapse]
})

export class ProfileComponent implements OnInit {

    user: User;
    createdTasks: Task[];
    responsibleTasks: Task[];

    public isCollapsedCreated:boolean = false;
    public isCollapsedResponsible:boolean = false;

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService) {
    }

    ngOnInit() {
        let title = new Title('');
        title.setTitle('View Profile');

        this.activatedRoute.params.subscribe((params: Params) => {
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
                    this.createdTasks = data.json().data;
                } else {
                    alert(data.json().message);
                }
            });

            this.httpService.getData('tasks.json', null).subscribe((data: Response) => {
                //this.httpService.getData('userResponsibleTasks/' + params['id'], null).subscribe((data: Response) => {
                if (data.json().success == true) {
                    this.responsibleTasks = data.json().data;
                } else {
                    alert(data.json().message);
                }
            });

        });
    }
}