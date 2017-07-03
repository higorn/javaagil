import {async, ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {LoginComponent} from './login.component';
import {CustomMdModule} from '../custom-md/custom-md.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterTestingModule} from '@angular/router/testing';
import {Location} from '@angular/common';
import {routes} from '../app-routing/app-routing.module';
import {HomeComponent} from '../home/home.component';
import {By} from '@angular/platform-browser';
import {FormBuilder, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AccountService} from '../service/account.service';
import {blankdUser, invalidUser, validUser} from '../mocks';
import {HttpModule} from '@angular/http';

describe('LoginComponent', () => {
    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;
    let location: Location;
    let spy;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                LoginComponent,
                HomeComponent,
            ],
            imports: [
                FormsModule,
                ReactiveFormsModule,
                RouterTestingModule.withRoutes(routes),
                BrowserAnimationsModule,
                CustomMdModule,
                HttpModule,
            ],
            providers: [
                FormBuilder,
                AccountService,
            ]
        }).compileComponents();
    }));

    beforeEach(inject([Location], (_location: Location) => {
        location = _location;
        fixture = TestBed.createComponent(LoginComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    function updateForm(name: string, password: string) {
        component.loginForm.controls['name'].setValue(name);
        component.loginForm.controls['password'].setValue(password);
    }

    it('should be created', async(() => {
        expect(component).toBeTruthy();
    }));

    it('should have default props', () => {
        expect(component.user).toBeNull();
    });

    it('with form changes should update form value', () => {
        updateForm(validUser.name, validUser.password);
        expect(component.loginForm.value).toEqual(validUser);
    });

    it('with blank form, the isValidForm should be false', () => {
        updateForm(blankdUser.name, blankdUser.password);
        expect(component.isValidForm).toBeFalsy();
    });

    it('with valid user should login success', done => {
        const testLogin = () => {
            expect(component.user).toEqual(validUser);
            expect(fixture.debugElement.query(By.css('#inputUser')).nativeElement.value).toBe(validUser.name);
            expect(fixture.debugElement.query(By.css('#inputPassword')).nativeElement.value).toBe(validUser.password);
            expect(localStorage.getItem('authToken')).not.toBeNull();
            expect(spy.calls.any()).toBeTruthy();
            expect(location.path()).toBe('/home');
            done();
        }

        const loginService = TestBed.get(AccountService);
        spy = spyOn(loginService, 'get').and.returnValue(Promise.resolve({name: 'Nicanor', authToken: 'abc'}));

        updateForm(validUser.name, validUser.password);

        localStorage.removeItem('authToken');
        component.onLogin();
        fixture.detectChanges();

        setTimeout(testLogin);
    });

    it('with invalid user, should login fail', done => {
        const testLogin = () => {
            fixture.detectChanges();
            expect(component.user).toEqual(invalidUser);
            expect(fixture.debugElement.query(By.css('#inputUser')).nativeElement.value).toBe(invalidUser.name);
            expect(fixture.debugElement.query(By.css('#inputPassword')).nativeElement.value).toBe(invalidUser.password);
            expect(localStorage.getItem('authToken')).toBeNull();
            expect(component.message).toEqual('Login inválido');
            expect(fixture.debugElement.query(By.css('#loginMessage'))
                .nativeElement.textContent).toBe('Login inválido');
            expect(spy.calls.any()).toBeTruthy();
            done();
        }

        const loginService = TestBed.get(AccountService);
        spy = spyOn(loginService, 'get').and.returnValue(Promise.reject(new Error('Login inválido').message));

        updateForm(invalidUser.name, invalidUser.password);

        localStorage.removeItem('authToken');
        component.onLogin();

        setTimeout(testLogin);
    });
});
