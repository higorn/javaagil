import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppRoutingModule} from './app-routing/app-routing.module';

import {AppComponent} from './app.component';
import {CustomMdModule} from './custom-md/custom-md.module';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from './in-memory-data-service';
import {HttpModule} from '@angular/http';
import {AccountService} from './service/account.service';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HomeComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        CustomMdModule,
        // InMemoryWebApiModule.forRoot(InMemoryDataService),
        HttpModule,
    ],
    providers: [
        AccountService,
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
