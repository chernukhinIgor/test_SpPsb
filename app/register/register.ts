import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';

const route = './app/register/';

@Component({
    selector: 'register-comp',
    templateUrl: route + 'register.html',
    styleUrls: [route + 'style.css'],
    providers: [HttpService]
})

export class RegisterComponent {

}