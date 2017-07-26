import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import { AuthenticationService } from '../_services/index';
import { Router } from '@angular/router';

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

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService) { }

    ngOnInit() {
        // reset login status
        this.authenticationService.logout();
    }

    login() {
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password)
            .subscribe(result => {
                if (result === true) {
                    this.router.navigate(['/']);
                } else {
                    this.error = 'Email or password is incorrect';
                    this.loading = false;
                }
            });
    }
}