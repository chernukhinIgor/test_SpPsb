import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';

const route = './app/login/';

@Component({
    selector: 'login-comp',
    templateUrl: route + 'login.html',
    styleUrls: [route + 'style.css'],
    providers: [HttpService]
})

export class LoginComponent {
    constructor() {
        console.log('aaaaaaaaaaa')
    }

    ngOnInit() {

    }
}