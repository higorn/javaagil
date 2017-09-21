import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import {LivrosComponent} from './livros/livros.component';
import {LivroDetalheComponent} from './livros/livro-detalhe/livro-detalhe.component';
import { RankingComponent } from './ranking/ranking.component';

export const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    children: [
      { path: 'livros', component: LivrosComponent },
      { path: 'livros/:id', component: LivroDetalheComponent },
      { path: 'ranking', component: RankingComponent },
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
      RouterModule,
  ]
})
export class AppRoutingModule { }
