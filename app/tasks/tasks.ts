import { Component, OnInit} from '@angular/core';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { Task} from '../classes';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { Title } from '@angular/platform-browser';
const route = './app/tasks/';
import { PagerService } from '../pagination_service/index';


@Component({
    selector: 'tasks-comp',
    templateUrl: route + 'tasks.html',
    providers: [HttpService]
})

export class TasksComponent implements OnInit {

    tasks: Task[];
    constructor(private httpService: HttpService, private pagerService: PagerService){}

    // array of all items to be paged
    private allItems: any[];

    // pager object
    pager: any = {};

    // paged items
    pagedItems: any[];

    ngOnInit(){
        let title = new Title('');
        title.setTitle('View Tasks');
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData('tasks', options).subscribe((data: Response) => {
            this.allItems = data.json().data;

            // initialize to page 1
            this.setPage(1);
        });


    }

    setPage(page: number) {
        if (page < 1 || page > this.pager.totalPages) {
            return;
        }

        // get pager object from service
        this.pager = this.pagerService.getPager(this.allItems.length, page);

        // get current page of items
        this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex + 1);
    }
}