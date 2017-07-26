import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

@Injectable()
export class HttpService{

    constructor(private http: Http){ }
    getData(url: string, params: URLSearchParams) {
        var jsonObject = this.http.get(url, params);
        return jsonObject;
    }

    postData(url: string, params: any) {
        return this.http.post(url, params);
    }


    putData(url: string, params: any) {
        return this.http.post(url, params);
    }

    deleteData(url: string, params: any) {
        return this.http.delete(url, params);
    }
}