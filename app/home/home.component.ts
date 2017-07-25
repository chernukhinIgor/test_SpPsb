import {Component,OnInit} from '@angular/core';
const route = './app/home/';

@Component({
    selector: 'home-app',
    templateUrl: route + 'home.html'
})
export class HomeComponent implements OnInit{
    page = 'home';
    constructor() {}

    ngOnInit(){

    }
}