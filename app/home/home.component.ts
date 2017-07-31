import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Params} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { Title } from '@angular/platform-browser';
import { WebSocketService }       from "../_services/websocket.service";
import { Task, User} from '../classes'
const route = './app/home/';

@Component({
    selector: 'home-app',
    templateUrl: route + 'home.html',
    providers: [HttpService, WebSocketService]
})
export class HomeComponent implements OnInit{
    page = 'home';
    tasks: Task[];
    users: User[];
    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService, private webSocketService: WebSocketService) {}

    ngOnInit(){

         //this.webSocketService.start( 'ws://localhost:3000/cable' );

        let title = new Title('');
        title.setTitle('Dashboard');
        this.getNewTasks();
        this.getNewUsers();
    }

    getNewTasks () {
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData('tasks', options).subscribe((data: Response) => {
            if(data.json().success == true) {
                let tasks = data.json().data;
                this.tasks= tasks.slice(-3)
                console.log(this.tasks)
            } else {
                alert(data.json().message)
            }
        });
    }

    getNewUsers() {
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData('users', options).subscribe((data: Response) => {
            if (data.json().success == true) {
                let users = data.json().data;
                this.users = users.slice(-3)
            } else {
                alert(data.json().message)
            }
        });
    }
}