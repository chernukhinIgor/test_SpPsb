import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Params, Router} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
const route = './app/register_verified/';

@Component({
    selector: 'register-verified-comp',
    templateUrl: route + 'register_verified.html',
    providers: [HttpService]
})
export class RegisterVerifiedComponent {
    id = '';
    token = '';
    verifiedResult = false;

    constructor(private router: Router,
                private activatedRoute: ActivatedRoute,
                private httpService: HttpService) {}

    ngOnInit(){
        this.activatedRoute.queryParams.subscribe((params: Params) => {
            if(params['id'] && params['token']) {
                if(params['id'].match(/[^0-9]/) == null ){
                    this.id = params['id'];
                }
                else {
                    alert('ID is invalid');
                    this.router.navigate(['/login']);
                }

                if(params['token'].match(/[^A-Za-z0-9-.]/) == null ) {
                    this.token = params['token'];
                }
                else {
                    alert('Token is invalid');
                    this.router.navigate(['/login']);
                }
            } else {
                this.router.navigate(['/login']);
            }
        });

        this.httpService.getData('tasks.json', null).subscribe((data: Response) =>
        //this.httpService.getData('confirmEmail?id='+this.id+'&token='+this.token, null).subscribe((data: Response) =>
            this.verifiedResult = data.json().success);
    }
}