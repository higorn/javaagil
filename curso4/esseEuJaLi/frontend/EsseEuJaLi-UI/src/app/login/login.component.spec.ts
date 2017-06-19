import {async, ComponentFixture, fakeAsync, inject, TestBed, tick} from '@angular/core/testing';

import {LoginComponent} from './login.component';
import {CustomMdModule} from '../custom-md/custom-md.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {Location} from '@angular/common';
import {routes} from '../app-routing/app-routing.module';
import {HomeComponent} from '../home/home.component';

describe('LoginComponent', () => {
    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;
    let location: Location;
    let router: Router;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [
                LoginComponent,
                HomeComponent,
            ],
            imports: [
                RouterTestingModule.withRoutes(routes),
                BrowserAnimationsModule,
                CustomMdModule,
            ],
            // providers: [
            //     Location,
            //     {provide: Router, useClass: MockRouter},
            // ]
        })
            .compileComponents();
    });

    // beforeEach(inject([Router], (_router: Router) => {
    beforeEach(() => {
        router = TestBed.get(Router);
        location = TestBed.get(Location);
        fixture = TestBed.createComponent(LoginComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
        router.initialNavigation();
    });

    it('should be created', () => {
        expect(component).toBeTruthy();
    });

    it('should login', () => {
        component.onLogin().then(() => {
            expect(location.path()).toBe('/home');
        });
    });
});
