import { Component } from '@angular/core';
import { AuthenticationService } from '../_services/index';
import { Router } from '@angular/router';
import { Account } from '../classes';
import { Notify } from '../notify/index';

@Component({
    selector: 'myNavbar',
    templateUrl: 'app/navbar/navbar.html'
})
export class myNavbar {
    navDropDown = false;
    account: Account;
    dropDownButtonItems: Array<any> = [{
        text: 'My Profile',
    }, {
        text: 'Logout'
    }];

    constructor (private router: Router, private authenticationService: AuthenticationService) {}


    ngOnInit() {
        this.account = JSON.parse(localStorage.getItem('currentUser'));

    }

    pushNotify() {
        Notify.appendNotify('title', 'mess', 'red');
    }

    logout () {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }

    onSplitButtonItemClick(dataItem: any) {
        console.log(dataItem.text);
        if (dataItem.text == 'My Profile') {
            console.log(this.router);
            this.router.navigate(['profile']);
        }
        else {
            this.logout();
        }
    }
}