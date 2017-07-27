import { Component, trigger, transition, style, animate } from '@angular/core';

@Component({
    selector: 'app-notify',
    animations: [
        trigger(
            'enterAnimation', [
                transition(':enter', [
                    style({transform: 'translateY(100%)', opacity: 0}),
                    animate('500ms', style({transform: 'translateY(0)', opacity: 1}))
                ]),
                transition(':leave', [
                    style({transform: 'translateY(0)', opacity: 1}),
                    animate('500ms', style({transform: 'translateY(100%)', opacity: 0}))
                ])
            ]
        )
    ],
    template: `
        <div class="notify-msg">
            
        </div>
        <!--<button (click)="show[1] = !show[1]">toggle show 1 ({{show[1]}})</button>-->
        <!--<button (click)="show[2] = !show[2]">toggle show 2 ({{show[2]}})</button>-->
  `
})
export class Animate {
    show:boolean[] = [false, false];

    appendNotify() {
        `<div [@enterAnimation] data-notify="container" class="alert alert-success" role="alert">
                <button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>
                <span data-notify="icon"></span>
                <span data-notify="title">1</span>
                <span data-notify="message">2</span>
                <div class="progress" data-notify="progressbar">
                    <div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>
                </div>
                <a href="/" target="_blank" data-notify="url"></a>
            </div>`
    }
}