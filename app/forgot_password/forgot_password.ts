import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import { Router} from '@angular/router';
import { RegistrationService} from "../_services/registration.service"

const route = './app/forgot_password/';

@Component({
    selector: 'forgot-password-comp',
    templateUrl: route + 'forgot_password.html',
    styleUrls: [route + 'style.css'],
    providers: [HttpService]
})

export class ForgotPasswordComponent {
    loading = false;
    error = '';
    email = '';
    showModal = false;
    constructor(private router: Router,
                private registrationService: RegistrationService) {}

    ngOnInit(){
        // if(localStorage.getItem('currentUser') !== null) {
        //     this.router.navigate(['/']);
        // }
    }

    onSubmit(){
        this.loading = true;
        this.registrationService.forgotPassword(this.email)
            .subscribe(result => {
                if (result === true) {
                    // this.router.navigate(['/login']);
                    this.showModal = true;
                } else {
                    this.error = 'Something is incorrect';
                    this.loading = false;
                }
            });
    }

    onClick() {
        this.router.navigate(['login']);
    }
}