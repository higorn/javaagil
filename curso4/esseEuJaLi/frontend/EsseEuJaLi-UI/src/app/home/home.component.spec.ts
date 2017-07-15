import {async, ComponentFixture, inject, TestBed} from '@angular/core/testing';

import { HomeComponent } from './home.component';
import {RouterTestingModule} from '@angular/router/testing';
import {routes} from '../app-routing/app-routing.module';
import {Location} from '@angular/common';
import {LoginComponent} from '../login/login.component';
import {CustomMdModule} from '../custom-md/custom-md.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AccountService} from '../service/account.service';
import {HttpModule} from '@angular/http';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let location: Location;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
          HomeComponent,
          LoginComponent,
      ],
      imports: [
        RouterTestingModule.withRoutes(routes),
        CustomMdModule,
        FormsModule,
        ReactiveFormsModule,
        HttpModule,
      ],
      providers: [
        AccountService,
      ]
    })
    .compileComponents();
  }));

  beforeEach(inject([Location], (_location: Location) => {
    location = _location;
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it(`should have as title 'app'`, async(() => {
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('app');
  }));

  it('should render title in a h1 tag', async(() => {
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to app!!');
  }));

  it('should logout', done => {
    const testNavigation = () => {
      expect(location.path()).toBe('/login');
      done();
    }
    component.onLogout();
    setTimeout(testNavigation);
  });
});
