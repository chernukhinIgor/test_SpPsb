import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

@Injectable()
export class HttpService{

    constructor(private http: Http) {
    }

    initHeader(){
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        var token = currentUser && currentUser.token;
        return { headers: new Headers({'Authorization': token}) };
    }

    getData(url: string, params: any) {
        console.log(this.initHeader());
        var jsonObject = this.http.get(apiUrl + url, this.initHeader());
        return jsonObject;
    }

    postData(url: string, params: any) {
        return this.http.post(apiUrl + url, params, this.initHeader());
    }


    putData(url: string, params: any) {
        return this.http.put(apiUrl + url, params, this.initHeader());
    }

    deleteData(url: string, params: any) {
        return this.http.delete(apiUrl + url, this.initHeader());
    }
}