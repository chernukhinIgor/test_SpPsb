import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

@Injectable()
export class HttpService{

    constructor(private http: Http){ }
    getData(url: string, params: URLSearchParams) {
        var jsonObject = this.http.get(url, params);
        if (jsonObject['success'] = "true") {
            return jsonObject['data'];
        } else {
            return jsonObject['error'];
        }
    }

    postData(url: string, params: any) {
        return {id: 2};
        //return this.http.post(url, params);
    }


    putData(url: string, params: any) {
    return true;
        //return this.http.post(url, params);
    }
}