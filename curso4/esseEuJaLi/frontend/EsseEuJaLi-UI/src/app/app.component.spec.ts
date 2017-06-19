import { TestBed, async } from '@angular/core/testing';

import { AppComponent } from './app.component';
import {AppRoutingModule} from './app-routing/app-routing.module';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {CustomMdModule} from './custom-md/custom-md.module';
import {APP_BASE_HREF} from '@angular/common';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
          HomeComponent,
          LoginComponent,
      ],
      imports: [
          AppRoutingModule,
          CustomMdModule,
      ],
      providers: [{provide: APP_BASE_HREF, useValue : '/' }]
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

});
