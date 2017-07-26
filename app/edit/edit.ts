import { Component} from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { FormsModule }   from '@angular/forms';
import { Task} from '../classes'
import { Title } from '@angular/platform-browser';

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
        this.activatedRoute.params.subscribe((params: Params) => {
            let title = new Title('');
            this.users = this.getUsers();
            this.id = params['id'];
            if(this.id) {
                title.setTitle('Edit Task');
                //let res = this.httpService.getData('/api/get/' + this.id, null);
                this.httpService.getData('task/'+this.id, null).subscribe((data: Response) => this.task=data.json().data[0]);
            } else {
                title.setTitle('Create Task');
                this.task = new Task;
            }
        });
    }

    onSubmit() {
        if(this.id) {
            let res = this.httpService.putData('task/', this.task);
        } else {
            let res = this.httpService.postData('task/', this.task );
            location.href = '/task/edit/' + res.id
        }
    }

    getUsers() {
        let options: URLSearchParams = new URLSearchParams();
        options.set('params','id');
        options.set('params','name');
        this.httpService.getData('users/', options).subscribe((data: Response) => { return data.json().data});
    }

    cancel () {
        window.history.back();
    }

}