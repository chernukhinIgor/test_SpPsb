import { Component} from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { HttpService} from '../http.service';
import { FormsModule }   from '@angular/forms';
import { Task} from '../classes'

const element = 'edit';
const route = './app/' + element + '/';

@Component({
    selector: 'home-app',
    templateUrl: route + element + '.html',
    providers: [HttpService]
})
export class Edit {
    id: string;
    task: Task;
    myModel: any;
    constructor(public router: Router, private activatedRoute: ActivatedRoute, private httpService: HttpService) {}

    ngOnInit() {
        // subscribe to router event
        this.activatedRoute.params.subscribe((params: Params) => {
            this.id = params['id'];
            if(this.id) {
                let res = this.httpService.getData('/api/get/' + this.id, null);

                console.log(res);

                this.task = res[0];
            } else {
                this.task = new Task;
            }
        });
    }

    onSubmit(f: FormsModule) {
        if(this.id) {
            let res = this.httpService.putData('/api/get/' + this.id, this.task);
        } else {
            let res = this.httpService.postData('/api/get/', this.task );
            location.href = '/task/edit/' + res.id
        }
    }

}