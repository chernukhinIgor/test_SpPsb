import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'

@Injectable()
export class RegistrationService {

    constructor(private http: Http) {
    }

    register(name: string, surname: string, email: string, password: string): Observable<boolean> {
        // return this.http.post('/api/registration', JSON.stringify({ name: name, surname: surname, email: email, password: password }))
        return this.http.get('task.json')
            .map((response: Response) => {
                let resp = response.json()
                if (resp.success == true) {
                    return true;
                } else {
                    // return false to indicate failed registration
                    return false;
                }
            });
    }

    forgotPassword(email: string): Observable<boolean> {
        // return this.http.post('/api/forgotpassword', JSON.stringify({ email: email }))
        return this.http.get('task.json')
            .map((response: Response) => {
                let resp = response.json()
                if (resp.success == true) {
                    return true;
                } else {
                    // return false to indicate failed registration
                    return false;
                }
            });
    }
}