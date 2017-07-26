import {Component, Injectable} from '@angular/core';
import { Router, CanActivate, CanActivateChild } from '@angular/router'

@Injectable()
export class AuthGuard implements CanActivate {
    constructor(private router: Router) { }

    canActivate() {
        if (this.checkLogin()) {
            return true;
        }
        this.router.navigate(['login']);
        return false;
    }

    private checkLogin() {
        return true;
    }

    canDeactivate() {

    }
}