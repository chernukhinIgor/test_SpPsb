import { Component } from '@angular/core';

@Component({
    selector: 'my-app',
    template: `<div>
        <h1>Маршрутизация в Angular 2</h1>
        <router-outlet></router-outlet>
    </div>`
})
export class AppComponent {
    name= '';
}