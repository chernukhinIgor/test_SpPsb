import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import { Router, ActivatedRoute, Params} from '@angular/router';
import { RegistrationService } from '../_services/index';

const route = './app/new_password/';

@Component({
    selector: 'new-password-comp',
    templateUrl: route + 'new_password.html',
    styleUrls: [route + 'style.css'],
    providers: [HttpService]
})

export class NewPasswordComponent {
    loading = false;
    error = '';
    passMatch = false;
    id = '';
    password: string;
    confirmPassword: string;
    showModal = false;
    constructor(private router: Router,
                private registrationService: RegistrationService,
                private activatedRoute: ActivatedRoute) {}


    ngOnInit(){
        // if(localStorage.getItem('currentUser') !== null) {
        //     this.router.navigate(['/']);
        // }

        this.activatedRoute.params.subscribe((params: Params) => {
            if(params['id']) {
                this.id = params['id'];
            } else {
                this.router.navigate(['/login']);
            }
        });
    }


    passwordsMatch(){
        this.passMatch = (this.password == this.confirmPassword);
    }


    newPassword(){
        this.loading = true;
        this.registrationService.newPassword(this.id, this.password)
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