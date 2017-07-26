import { Component } from '@angular/core';
import { AuthenticationService } from './_services/index';
import { Router } from '@angular/router';

@Component({
    selector: 'my-app',
    templateUrl: 'app/app.components.html'
})
export class AppComponent {
    page = window.location.pathname;
    navbar = false;
    constructor (private router: Router, private authenticationService: AuthenticationService) {}
    ngOnInit() {
        if (this.page != '/login' && this.page != '/register'){
            this.navbar = true;
        } else {
            this.navbar = false;
        }
    }

    logout () {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }
}