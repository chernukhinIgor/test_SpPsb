import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

@Injectable()
export class HttpService{

    constructor(private http: Http){ }
    getData(url: string, params: URLSearchParams) {
        var jsonObject = this.http.get(apiUrl + url, params);
        return jsonObject;
    }

    postData(url: string, params: any) {
        return this.http.post(apiUrl + url, params);
    }


    putData(url: string, params: any) {
        return this.http.put(apiUrl + url, params);
    }

    deleteData(url: string, params: any) {
        return this.http.delete(apiUrl + url.toLowerCase(), params).subscribe();
    }
}