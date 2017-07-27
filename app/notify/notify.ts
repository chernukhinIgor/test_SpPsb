import {Component, ElementRef, trigger, transition, style, animate} from '@angular/core';
import {MyAppAnimation} from './animate';
import {NotifyMess} from '../classes';

@Component({
    selector: 'app-notify',
    animations: [MyAppAnimation],
    templateUrl: 'app/notify/notify.html'
})

export class Notify {
    public static notifys : NotifyMess[] = [];

    constructor(private elementRef:ElementRef) {}

    public static appendNotify(title: string, message: string) {
        let newMess = new NotifyMess;

        newMess.message = message;
        newMess.title = title;

        Notify.notifys.push(newMess)
    }

    removeNotify(id: number) {
        Notify.notifys.splice(id, 1);
    }

    getNotify() {
        return Notify.notifys;
    }
}