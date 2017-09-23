import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {User} from '../model/user';
import 'rxjs/add/operator/toPromise';
import {UserAccount} from '../model/user-account';
import { isArray } from 'rxjs/util/isArray';

@Injectable()
export class AccountService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private baseUrl = '/esse-eu-ja-li/api/v1';
    // private baseUrl = 'api';

    constructor(private http: Http) {
    }

    login(user: User): Promise<UserAccount> {
        const headers = new Headers(this.headers);
        headers.append('Authorization', 'Basic ' + btoa(user.name + ':' + user.password));
        console.log(user);
        return this.http.get(`${this.baseUrl}/account/?name=${user.name}&senha=${user.password}`, {headers: headers})
            .toPromise()
            .then(resp => {
                console.log(resp.json().data);
                const data = resp.json().data;
                if (isArray(data) && data.length === 0) {
                  throw new Error('Login inválido');
                }
                return resp.json().data[0] as UserAccount;
            }).catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('Erro ao buscar dados', error);
        return Promise.reject(error);
    }

    logout(): any {
        const account: UserAccount = JSON.parse(localStorage.getItem('account')) || {token: null};
        const headers = new Headers(this.headers);
        headers.append('Authorization', account.senha);
        return this.http.get(`${this.baseUrl}/logout`, {headers: headers})
            .toPromise()
            .then(resp => {
                console.log(resp.json());
            }).catch(this.handleError);
    }
}
