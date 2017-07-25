import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Params} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { Title } from '@angular/platform-browser';
import { Task} from '../classes'
import { User} from '../classes'
const route = './app/home/';

@Component({
    selector: 'home-app',
    templateUrl: route + 'home.html',
    providers: [HttpService]
})
export class HomeComponent implements OnInit{
    page = 'home';
    tasks: Task[];
    users: User[];
    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService) {}

    ngOnInit(){
        let title = new Title('');
        title.setTitle('Dashboard');
        this.getNewTasks();
        this.getNewUsers();
    }

    getNewTasks () {
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData('tasksnew.json', options).subscribe((data: Response) => this.tasks=data.json().data);
    }

    getNewUsers() {
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData('usersnew.json', options).subscribe((data: Response) => this.users=data.json().data);
    }
}