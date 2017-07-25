import { Component} from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { HttpService} from '../http.service';
import { User} from '../classes'
import { FormsModule }   from '@angular/forms';

const element = 'user_edit';
const route = './app/' + element + '/';

@Component({
    selector: 'home-app',
    templateUrl: route + element + '.html',
    providers: [HttpService]
})
export class UserEdit {
    id: string;
    user: User;
    myModel: any;
    constructor(public router: Router, private activatedRoute: ActivatedRoute, private httpService: HttpService) {}

    ngOnInit() {
        // subscribe to router event
        this.activatedRoute.params.subscribe((params: Params) => {
            this.id = params['id'];
            if(this.id) {
                //let res = this.httpService.getData('/api/user/' + this.id, null);
                let res = this.httpService.getData('user.json', null);
                this.user = res[0];
            } else {
                this.user = new User;
            }
        });
    }

    onSubmit(f: FormsModule) {
        if(this.id) {
            let res = this.httpService.putData('/api/get/' + this.id, this.user);
        } else {
            let res = this.httpService.postData('/api/get/', this.user );
            location.href = '/task/edit/' + res.id
        }
        console.log(this.user);
    }

}