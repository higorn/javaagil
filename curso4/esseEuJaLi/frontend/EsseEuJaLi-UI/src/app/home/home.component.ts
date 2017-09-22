import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../service/account.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    pages = [
      { link: 'livros', label: 'Livros' },
      { link: 'ranking', label: 'Ranking' },
      { link: 'perfil', label: 'Meus pontos' }
    ];
    constructor(
      private router: Router,
      private service: AccountService,
    ) {
    }

    ngOnInit() {
    }

    onLogout() {
        // this.service.logout();
        localStorage.removeItem('account');
        this.router.navigate(['login']);
    }
}
