import {InMemoryDbService} from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {

    createDb(): {} {
        const account = [
            {id: 1, name: 'nicanor', displayName: 'Nicanor', token: 'abc'},
        ];
        return {account};
    }

}
