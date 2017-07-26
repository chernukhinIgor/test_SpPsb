import { Component, OnInit} from '@angular/core';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { User} from '../classes'
import { Title } from '@angular/platform-browser';
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

    ngOnInit(){let title = new Title('');
        title.setTitle('View Users');
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData(apiUrl +'users', options).subscribe((data: Response) => {
            if(data.json().success == true) {
                this.users=data.json().data
            } else {
                alert(data.json().message)
            }
        });
    }

    toggle(){
        this.condition=true;
    }
}