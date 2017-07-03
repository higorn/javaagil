import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {User} from '../model/user';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class AccountService {

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {
    }

    get(user: User): Promise<Account> {
        const authHeaders = new Headers({'Authorization': 'Basic ' + btoa(user.name + ':' + user.password)});
        return this.http.get('api/account', {headers: authHeaders})
            .toPromise()
            .then(resp => {
                console.log(resp.json().data);
                return resp.json().data as Account;
            }).catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('Erro ao buscar dados', error);
        return Promise.reject(error);
    }
}
