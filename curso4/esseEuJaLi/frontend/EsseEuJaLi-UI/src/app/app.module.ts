import 'reflect-metadata';

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CustomMdModule} from './custom-md/custom-md.module';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from './in-memory-data-service';
import {HttpModule} from '@angular/http';
import {AccountService} from './service/account.service';
import { LivrosComponent } from './livros/livros.component';
import {LivroService} from './service/livro.service';
import { LivroDetalheComponent } from './livros/livro-detalhe/livro-detalhe.component';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HomeComponent,
        LivrosComponent,
        LivroDetalheComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        CustomMdModule,
        HttpModule,
        InMemoryWebApiModule.forRoot(InMemoryDataService, {apiBase: 'esse-eu-ja-li/api/v1/'}),
    ],
    providers: [
        AccountService,
        LivroService,
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
