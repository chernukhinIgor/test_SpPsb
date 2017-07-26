import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Params} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { Title } from '@angular/platform-browser';
import { Task, User} from '../classes'
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
        this.httpService.getData(apiUrl +'tasks', options).subscribe((data: Response) => {
            if(data.json().succes) {
                let tasks = data.json().data;
                this.tasks= tasks.slice(Math.max(tasks.length - 3, 1))
            } else {
                alert(data.json().message)
            }
        });
    }

    getNewUsers() {
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData(apiUrl +'users', options).subscribe ((data: Response) => {
            if (data.json().succes) {
                let users = data.json().data;
                this.users = users.slice(Math.max(users.length - 3, 1))
            } else {
                alert(data.json().message)
            }
        });
    }
}