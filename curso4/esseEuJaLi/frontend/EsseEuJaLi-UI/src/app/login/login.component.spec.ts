import {async, ComponentFixture, inject, TestBed} from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {CustomMdModule} from '../custom-md/custom-md.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Router} from '@angular/router';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let router: Router;

  class MockRouter {
      navigate(commands: any[]): Promise<boolean> {
        return Promise.resolve(true);
      }
  }
  const mockRouter = {
    navigate: jasmine.createSpy('navigate')
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [
          BrowserAnimationsModule,
          CustomMdModule,
      ],
      providers: [
        Location,
        { provide: Router, useClass: MockRouter },
      ]
    })
    .compileComponents();
  }));

  beforeEach(inject([Router], (_router: Router) => {
      router = _router;
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should login', () => {
      const spy = spyOn(router, 'navigate');
    component.onLogin();
    expect(router.navigate).toHaveBeenCalledWith(['/home']);
  });
});
