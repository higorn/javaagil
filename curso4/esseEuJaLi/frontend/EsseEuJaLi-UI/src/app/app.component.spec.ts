import {TestBed, async, inject, ComponentFixture} from '@angular/core/testing';

import { AppComponent } from './app.component';
import {AppRoutingModule, routes} from './app-routing.module';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {CustomMdModule} from './custom-md/custom-md.module';
import {Location} from '@angular/common';
import {APP_BASE_HREF} from '@angular/common';
import {RouterTestingModule} from '@angular/router/testing';
import {Router} from '@angular/router';
import {FormBuilder, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AccountService} from './service/account.service';
import {HttpModule} from '@angular/http';

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let app: AppComponent;
  let location: Location;
  let router: Router;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
          HomeComponent,
          LoginComponent,
      ],
      imports: [
          RouterTestingModule.withRoutes(routes),
          AppRoutingModule,
          CustomMdModule,
          FormsModule,
          HttpModule,
          ReactiveFormsModule,
      ],
      providers: [
          FormBuilder,
          AccountService,
          {provide: APP_BASE_HREF, useValue : '/' }
      ]
    }).compileComponents();
  }));

  beforeEach(inject([Location, Router], (_location: Location, _router: Router) => {
    router = _router;
    location = _location;
    fixture = TestBed.createComponent(AppComponent);
    app = fixture.debugElement.componentInstance;
    router.initialNavigation();
    fixture.detectChanges();
  }));

  it('should create the app', async(() => {
    expect(app).toBeTruthy();
  }));

  it('should go to login if not loged', done => {
    const testNavigation = () => {
      expect(location.path()).toBe('/login');
      done();
    }
    localStorage.removeItem('authToken');
    app.ngOnInit();
    setTimeout(testNavigation);
  });

  it('should go to home if allready loged', done => {
    const testNavigation = () => {
      expect(location.path()).toBe('/home');
      localStorage.removeItem('authToken');
      done();
    }
    localStorage.setItem('authToken', 'abc');
    app.ngOnInit();
    setTimeout(testNavigation);
  });

});
