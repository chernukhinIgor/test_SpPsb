import {Directive, Input, HostBinding, ElementRef} from '@angular/core'
@Directive({selector: '[collapse]'})
export class Collapse {
    @HostBinding('class.collapsing')
    private isCollapsing:boolean

    @HostBinding('style.height')
    private height:string;


    @Input()
    private set collapse(value:boolean) {
        value?this.show():this.hide();
    }

    constructor(public el: ElementRef) {
    }

    hide(){
        this.height = this.el.nativeElement.scrollHeight +'px';
        setTimeout(() => {
            this.height = '0px';
            this.isCollapsing = true;
        },1);
    }
    show() {
        this.height = '0px';
        setTimeout(() => {
            this.height = this.el.nativeElement.scrollHeight + 'px';
            this.isCollapsing = true;
        },1);
    }

}