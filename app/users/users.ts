import { Component, OnInit} from '@angular/core';
import { Response} from '@angular/http';
import { HttpService} from '../http.service';
import { User} from '../classes'
import { Title } from '@angular/platform-browser';
const route = './app/users/';
import { PagerService } from '../pagination_service/index';

@Component({
    selector: 'users-comp',
    templateUrl: route + 'users.html',
    providers: [HttpService]
})

export class UsersComponent implements OnInit {

    users: User[];
    condition: boolean=false;

    constructor(private httpService: HttpService, private pagerService: PagerService){}

    // array of all items to be paged
    private allItems: any[];

    // pager object
    pager: any = {};

    // paged items
    pagedItems: any[];

    ngOnInit(){let title = new Title('');
        title.setTitle('View Users');
        let options: URLSearchParams = new URLSearchParams();
        this.httpService.getData('users', options).subscribe((data: Response) => {
            if(data.json().success == true) {
                this.allItems=data.json().data
                // initialize to page 1
                this.setPage(1);
            } else {
                alert(data.json().message)
            }
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