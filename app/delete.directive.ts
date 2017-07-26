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
        if (this.entityId) {
            this.httpService.deleteData(this.moduleName+'/'+this.entityId, null).subscribe((data: Response) => {
                if (data.json()['success'] == true) {
                    location.reload();
                } else {
                    alert(data.json()['message']);
                }
            });
        } else {
            alert('Invalid id');
        }
    }
}



