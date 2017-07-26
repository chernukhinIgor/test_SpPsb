import { Component } from '@angular/core';

@Component({
    selector: 'my-app',
    templateUrl: 'app/app.components.html'
})
export class AppComponent {
    page = window.location.pathname;
    navbar = false;
    constructor () {
        if (this.page != '/login' && this.page != '/register'){
            this.navbar = true;
        } else {
            this.navbar = false;
        }
    }
}