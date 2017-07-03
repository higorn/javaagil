import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  title = 'app';

  constructor(private router: Router) { }

  ngOnInit() {
  }

  onLogout() {
    localStorage.removeItem('authToken');
    this.router.navigate(['login']);
  }
}
