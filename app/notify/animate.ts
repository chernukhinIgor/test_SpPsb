import {trigger, transition, style, animate} from '@angular/core';

export const MyAppAnimation =
    trigger(
        'myAppAnimation', [
            transition(':enter', [
                style({transform: 'translateY(100%)', opacity: 0}),
                animate('500ms', style({transform: 'translateY(0)', opacity: 1}))
            ]),
            transition(':leave', [
                style({transform: 'translateY(0)', opacity: 1}),
                animate('500ms', style({transform: 'translateY(100%)', opacity: 0}))
            ])
        ]
    );
