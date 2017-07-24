import { Component, OnInit} from '@angular/core';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { User} from '../classes'
const route = './app/users/';


@Component({
    selector: 'users-comp',
    templateUrl: route + 'users.html',
    providers: [HttpService]
})

export class UsersComponent implements OnInit {

    users: User[];
    condition: boolean=false;

    constructor(private httpService: HttpService){}

    ngOnInit(){
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData('users.json', options).subscribe((data: Response) => this.users=data.json().data);
    }

    toggle(){
        this.condition=true;
    }
}