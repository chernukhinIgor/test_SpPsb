import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import { Router} from '@angular/router';
import { RegistrationService } from '../_services/index';
import { RegisterUser} from '../classes';

const route = './app/register/';

@Component({
    selector: 'register-comp',
    templateUrl: route + 'register.html',
    styleUrls: [route + 'style.css'],
    providers: [HttpService]
})

export class RegisterComponent {
    loading = false;
    error = '';
    passMatch = false;
    user: RegisterUser;
    confirmPassword: string;
    showModal = false;
    constructor(private router: Router,
                private registrationService: RegistrationService) {}


    ngOnInit(){
        // if(localStorage.getItem('currentUser') !== null) {
        //     this.router.navigate(['/']);
        // }
        this.user = new RegisterUser;
    }


    passwordsMatch(){
        this.passMatch = (this.user.password == this.confirmPassword);
    }


    register(){
        this.loading = true;
        this.registrationService.register(this.user.name, this.user.surname, this.user.email, this.user.password)
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