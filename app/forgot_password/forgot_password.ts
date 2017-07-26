import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import { Router} from '@angular/router';

const route = './app/forgot_password/';

@Component({
    selector: 'forgot-password-comp',
    templateUrl: route + 'forgot_password.html',
    styleUrls: [route + 'style.css'],
    providers: [HttpService]
})

export class ForgotPasswordComponent {
    constructor(private router: Router) {}

    ngOnInit(){
        // if(localStorage.getItem('currentUser') !== null) {
        //     this.router.navigate(['/']);
        // }
    }


    onClick() {
        this.router.navigate(['login']);
    }
}