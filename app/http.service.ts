import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

@Injectable()
export class HttpService{

    constructor(private http: Http){ }
    getData(url: string, params: URLSearchParams) {

        if(url == '/api/get/2') {

            return [
                {
                    "task_id":1,
                    "creator_user_id":1,
                    "name":"someName",
                    "responsible_user_id":1,
                    "description":"someDescription"
                }
            ];
        } else if (url == '/api/user/2') {
            return [
                {
                    "user_id":1,
                    "name":"someName",
                    "surname":"someSurname",
                    "telephone":"someTelephone",
                    "email":"someEmail",
                    "gender":"male",
                    "datebirth":"1991-02-22"
                }
            ];
        }

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