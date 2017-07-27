import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import { AuthenticationService } from '../_services/index';
import { Router } from '@angular/router';
import {LoginUser} from '../classes'
import { Notify} from "../notify/notify"

const route = './app/login/';

@Component({
    selector: 'login-comp',
    templateUrl: route + 'login.html',
    styleUrls: [route + 'style.css'],
    providers: [HttpService]
})

export class LoginComponent {
    model: any = {};
    loading = false;
    error = '';
    user: LoginUser;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService) { }

    ngOnInit() {
        // if(localStorage.getItem('currentUser') !== null) {
        //     this.router.navigate(['/']);
        // }
        this.user = new LoginUser;
    }

    login() {
        this.loading = true;
        this.authenticationService.login(this.user.email, this.user.password)
            .subscribe(result => {
                if (result === true) {
                    this.router.navigate(['/']);
                } else {
                    this.error = 'Email or password is incorrect';
                    Notify.appendNotify('title', this.error, 'red')
                    this.loading = false;
                }
            });
    }
}