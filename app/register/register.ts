import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import { Router} from '@angular/router';

const route = './app/register/';

@Component({
    selector: 'register-comp',
    templateUrl: route + 'register.html',
    styleUrls: [route + 'style.css'],
    providers: [HttpService]
})

export class RegisterComponent {
    constructor(private router: Router) {}

    ngOnInit(){
        // if(localStorage.getItem('currentUser') !== null) {
        //     this.router.navigate(['/']);
        // }
    }


    onSubmit(){

    }
    onClick() {

        this.router.navigate(['login']);
    }
}