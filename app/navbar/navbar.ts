import { Component } from '@angular/core';
import { AuthenticationService } from '../_services/index';
import { Router } from '@angular/router';

@Component({
    selector: 'myNavbar',
    templateUrl: 'app/navbar/navbar.html'
})
export class myNavbar {
    constructor (private router: Router, private authenticationService: AuthenticationService) {}
    ngOnInit() {
    }

    logout () {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }
}