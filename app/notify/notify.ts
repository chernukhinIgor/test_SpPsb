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

    public static appendNotify(title: string, message: string, color: string) {
        let newMess = new NotifyMess;

        newMess.message = message;
        newMess.title = title;
        newMess.uniqueID = Date.now();
        newMess.type = (function(color) {
            switch(color) {
                case 'red':
                    return 'alert-danger';
                case 'blue':
                    return 'alert-info';
                case 'yellow':
                    return 'alert-warning';
                default:
                    return 'alert-success';
            }
        })(color);

        Notify.notifys.push(newMess);
        Notify.removeByTimes(newMess.uniqueID);
    }

    static removeNotify(uniqueID: number) {
        for(let i = 0; i < Notify.notifys.length; i++){
            if(Notify.notifys[i].uniqueID === uniqueID) {
                Notify.notifys.splice(i, 1);
                break;
            }
        }
    }

    remove(uniqueID: number) {
        Notify.removeNotify(uniqueID);
    }

    getNotify() {
        return Notify.notifys;
    }

    static removeByTimes (uniqueID: number) {
        setTimeout(function () {
            Notify.removeNotify(uniqueID);
        }, 5000)
    }
}