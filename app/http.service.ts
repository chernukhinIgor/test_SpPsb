import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

@Injectable()
export class HttpService{
    private headers: Headers;

    constructor(private http: Http) {
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        var token = currentUser && currentUser.token;
        this.headers = new Headers({'Authorization': token, 'Content-Type': 'application/json'});
    }


    getData(url: string, params: any) {
        var jsonObject = this.http.get(apiUrl + url, this.headers);
        return jsonObject;
    }

    postData(url: string, params: any) {
        return this.http.post(apiUrl + url, params, this.headers);
    }


    putData(url: string, params: any) {
        return this.http.put(apiUrl + url, params, this.headers);
    }

    deleteData(url: string, params: any) {
        return this.http.delete(apiUrl + url, this.headers);
    }
}