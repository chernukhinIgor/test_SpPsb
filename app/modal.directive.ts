import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';

const template: string = '' +
    '<div class="modal-backdrop fade" [style.display]="showModal ? \'block\' : \'none\'"></div>\n' +
    '  <div class="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" [style.display]="showModal ? \'block\' : \'none\'">\n' +
    '    <div class="modal-dialog" role="document">\n' +
    '      <div class="modal-content">\n' +
    '        <div class="modal-header">\n' +
    '          <h3 class="modal-title">{{title}}</h3>\n' +
    '              <div class="modal-body">  </div>\n'+
    '              <p *ngIf="subTitle">{{subTitle}}</p>\n' +
    '          <ng-content></ng-content>\n' +
    '        </div>\n' +
    '    </div>\n' +
    '  </div>\n' +
    '</div>';

@Component({
    selector: 'modal',
    template
})

export class ModalDirective implements OnInit {
    @Input('show-modal') showModal: boolean;
    @Input('title') title: string;
    @Input('sub-title') subTitle: string;
    @Input('cancel-label') cancelLabel: string;
    @Input('positive-label') positiveLabel: string;

    @Output('closed') closeEmitter: EventEmitter < ModalResult > = new EventEmitter < ModalResult > ();
    @Output('loaded') loadedEmitter: EventEmitter < ModalDirective > = new EventEmitter < ModalDirective > ();
    @Output() positiveLabelAction = new EventEmitter();

    constructor() {}

    ngOnInit() {
        this.loadedEmitter.next(this);
    }

    show() {
        this.showModal = true;
    }

    hide() {
        this.showModal = false;
        this.closeEmitter.next({
            action: ModalAction.POSITIVE
        });
    }

    positiveAction() {
        this.positiveLabelAction.next(this);
        return false;
    }

    cancelAction() {
        this.showModal = false;
        this.closeEmitter.next({
            action: ModalAction.CANCEL
        });
        return false;
    }
}

export enum ModalAction { POSITIVE, CANCEL }

export interface ModalResult {
    action: ModalAction;
}