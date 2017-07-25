import {Directive, HostListener, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { HttpService} from './http.service';


@Directive({
    selector: '[deleteClick]'
})

export class DeleteDirective implements OnInit{

    constructor(private httpService: HttpService){}

    @Input() moduleName: string;
    @Input() entityId: number;

    ngOnInit(){
    }

    @Output() deleteClick: EventEmitter = new EventEmitter();

    @HostListener('click', ['$event'])
    onClick() {
        console.log(this.moduleName, this.entityId);
        if (this.entityId) {
            let res = this.httpService.deleteData('/api/delete/' + this.entityId, this.moduleName);
            console.log(res);
        } else {
            alert('Invalid id');
        }
    }
}



