import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {User} from '../model/user';
import {AccountService} from '../service/account.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    user: User = null;
    loginForm: FormGroup;
    isValidForm = true;
    message: string;

    constructor(
        private router: Router,
        private fb: FormBuilder,
        private service: AccountService,
    ) {
        this.loginForm = this.fb.group({
            'name': [undefined, Validators.required],
            'password': [undefined, Validators.required],
        });

        this.loginForm.valueChanges.subscribe(() => {
            this.isValidForm = this.loginForm.valid;
        })
    }

    ngOnInit() {
    }

    onLogin(): void {
        this.user = this.loginForm.value;
        this.service.login(this.user).then(account => {
            console.log(account);
            localStorage.setItem('account', JSON.stringify(account));
            this.router.navigate(['/home']);
        }, error => {
            this.message = error;
            console.log(error);
        })
    }
}
