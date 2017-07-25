import { Component} from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { FormsModule }   from '@angular/forms';
import { Task} from '../classes'

const element = 'edit';
const route = './app/' + element + '/';

//tmp variables

const users = [
    {
        id: '1',
        name: 'name One'
    },
    {
        id: '2',
        name: 'name two'
    },
    {
        id: '3',
        name: 'name three'
    },
    {
        id: '3',
        name: 'name four'
    }
];

@Component({
    selector: 'home-app',
    templateUrl: route + element + '.html',
    providers: [HttpService]
})
export class Edit {
    id: string;
    task: Task;
    myModel: any;
    users: any[];
    constructor(public router: Router, private activatedRoute: ActivatedRoute, private httpService: HttpService) {}

    ngOnInit() {
        // subscribe to router event
        this.activatedRoute.params.subscribe((params: Params) => {
            this.users = this.getUsers();
            this.id = params['id'];
            if(this.id) {
                //let res = this.httpService.getData('/api/get/' + this.id, null);
                this.httpService.getData('task.json', null).subscribe((data: Response) => this.task=data.json().data[0]);
            } else {
                this.task = new Task;
            }
        });
    }

    onSubmit() {
        if(this.id) {
            let res = this.httpService.putData('/api/get/' + this.id, this.task);
        } else {
            let res = this.httpService.postData('/api/get/', this.task );
            location.href = '/task/edit/' + res.id
        }
    }

    getUsers() {
        return users;
    }

}