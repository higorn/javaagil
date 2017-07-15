import {TestBed, inject, async} from '@angular/core/testing';

import {AccountService} from './account.service';
import {validAccount, validUser} from '../mocks';
import {HttpModule} from '@angular/http';
import {InMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from '../in-memory-data-service';

describe('AccountService', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                HttpModule,
                InMemoryWebApiModule.forRoot(InMemoryDataService, {apiBase: 'esse-eu-ja-li/api/v1/account/'}),
            ],
            providers: [
                AccountService,
            ]
        });
    });

    it('should be created', inject([AccountService], (service: AccountService) => {
        expect(service).toBeTruthy();
    }));

    it('with valid user, should get user account', async(() => {
        const service = TestBed.get(AccountService);
        const accountPromise = service.login(validUser);
        accountPromise.then(result => {
            expect(result).toEqual(validAccount);
        }).catch(error => {
            console.log('error: ' + error);
            fail(error);
        });
    }));
});
