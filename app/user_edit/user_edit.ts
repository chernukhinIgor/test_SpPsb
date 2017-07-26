import { Component} from '@angular/core';
import { ActivatedRoute, Params} from '@angular/router';
import { HttpService} from '../http.service';
import { Response} from '@angular/http';
import { User} from '../classes'
import { Title } from '@angular/platform-browser';

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
    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService) {}

    ngOnInit() {
        this.activatedRoute.params.subscribe((params: Params) => {
            this.id = params['id'];
            let title = new Title('');
            if(this.id) {
                title.setTitle('Edit User');
                this.httpService.getData('user/' + this.id, null).subscribe((data: Response) => {
                    if(data.json().success == true) {
                        this.user=data.json().data[0];
                    } else {
                        alert(data.json().message)
                    }
                });
            } else {
                title.setTitle('Create User');
                this.user = new User;
            }
        });
    }

    onSubmit() {
        if(this.id) {
            this.httpService.putData('user', this.user).subscribe((data: Response) => {
                if(data.json().success == true) {
                    location.href = '/user/get/' +this.id;
                } else {
                    alert(data.json().message)
                }
            });
        } else {
            this.httpService.postData('user', this.user ).subscribe((data: Response) => {
                if(data.json().success == true) {
                    location.href = '/user/get/' + data.json().data.userId;
                } else {
                    alert(data.json().message)
                }
            });
        }
    }

    cancel () {
        window.history.back();
    }

}