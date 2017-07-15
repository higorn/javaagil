import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {User} from '../model/user';
import 'rxjs/add/operator/toPromise';
import {UserAccount} from '../model/user-account';

@Injectable()
export class AccountService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private baseUrl = '/esse-eu-ja-li/api/v1/account';

    constructor(private http: Http) {
    }

    login(user: User): Promise<UserAccount> {
        const headers = new Headers(this.headers);
        headers.append('Authorization', 'Basic ' + btoa(user.name + ':' + user.password));
        return this.http.get(`${this.baseUrl}/login`, {headers: headers})
            .toPromise()
            .then(resp => {
                console.log(resp.json());
                return resp.json() as UserAccount;
            }).catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('Erro ao buscar dados', error);
        return Promise.reject(error);
    }

    logout(): any {
        const account: UserAccount = JSON.parse(localStorage.getItem('account')) || {token: null};
        const headers = new Headers(this.headers);
        headers.append('Authorization', account.token);
        return this.http.get(`${this.baseUrl}/logout`, {headers: headers})
            .toPromise()
            .then(resp => {
                console.log(resp.json());
                localStorage.removeItem('account');
            }).catch(this.handleError);
    }
}
