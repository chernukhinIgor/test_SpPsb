import { Component } from '@angular/core';
import { AuthenticationService } from '../_services/index';
import { Router } from '@angular/router';
import { Account } from '../classes';

@Component({
    selector: 'myNavbar',
    templateUrl: 'app/navbar/navbar.html'
})
export class myNavbar {
    navDropDown = false;
    account: Account;
    constructor (private router: Router, private authenticationService: AuthenticationService) {}
    ngOnInit() {
        this.account = JSON.parse(localStorage.getItem('currentUser'));

    }

    logout () {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }
}