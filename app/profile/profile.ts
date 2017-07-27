import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Response} from '@angular/http';
import {HttpService} from '../http.service';
import {Title} from '@angular/platform-browser';

const route = './app/profile/';

@Component({
    selector: 'profile-comp',
    templateUrl: route + 'profile.html',
    providers: [HttpService]
})

export class ProfileComponent implements OnInit {

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService) {
    }

    ngOnInit() {
        let title = new Title('');
        title.setTitle('View Profile');

        this.getProfile();
    }

    getProfile () {
    }

}