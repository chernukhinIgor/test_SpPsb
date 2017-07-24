import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

@Injectable()
export class HttpService{

    constructor(private http: Http){ }
    getData(url: string, params: URLSearchParams){
        var jsonObject = this.http.get(url, params);
        if (jsonObject['success'] = "true") {
            return jsonObject;
        } else {
            alert('Request success false');
        }
    }
}