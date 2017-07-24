import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

@Injectable()
export class HttpService{

    constructor(private http: Http){ }
    getData(url: string){
        this.http.get(url).subscribe(data => {
            if(data['success']) {
                return data['result']
            } else {
                alert(data['false']['mesage']);
                return null;
            }
        });
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