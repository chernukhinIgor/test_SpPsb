import { Component, Input } from '@angular/core';

const template: string = '' +
    '<kendo-dialog title="{{title}}"  (close)="close(\'cancel\')">\n' +
    '            <p style="margin: 30px; text-align: center;">{{content}}</p>\n' +
    '            <kendo-dialog-actions>\n' +
    '                <ng-content></ng-content>'
    '            </kendo-dialog-actions>\n' +
    '</kendo-dialog>';

@Component({
    selector: 'modal',
    template
})

export class ModalDirective {
    @Input('show-modal') showModal: boolean;
    @Input('title') title: string;
    @Input('content') content: string;
    @Input('yButton') yButton: string;
    @Input('nButton') nButton: string;

    constructor() {}

    show() {
        this.showModal = true;
    }
}
