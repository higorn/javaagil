import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Livro} from '../model/livro';

import 'rxjs/add/operator/toPromise';
import {UserAccount} from '../model/user-account';
import { UsuarioLivro } from '../model/usuario-livro';
import { UsuarioPonto } from '../model/usuario-ponto';

@Injectable()
export class LivroService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private baseUrl = '/esse-eu-ja-li/api/v1';

  constructor(private http: Http) {
  }

  getLivros(): Promise<Livro[]> {
    const account: UserAccount = JSON.parse(localStorage.getItem('account')) || {token: null};
    const headers = new Headers(this.headers);
    headers.append('Authorization', account.senha);
    return this.http.get(`${this.baseUrl}/livros`, {headers: headers})
        .toPromise()
        .then(resp => {
          console.log(resp.json());
          return resp.json().data as Livro[];
        }).catch(this.handleError);
  }

  getLivrosUsuario(): Promise<UsuarioLivro> {
    const account: UserAccount = JSON.parse(localStorage.getItem('account')) || {token: null};
    const headers = new Headers(this.headers);
    headers.append('Authorization', account.senha);
    return this.http.get(`${this.baseUrl}/usuarioLivro/${account.id}`, {headers: headers})
      .toPromise()
      .then(resp => {
        console.log(resp.json().data);
        return resp.json().data as UsuarioLivro;
      }).catch(this.handleError);
  }

  getLivro(id: string): Promise<Livro> {
    return this.getLivros().then(livros => livros.find(livro => livro.id === id));
  }

  updateLivrosUsuario(livro: Livro): Promise<UsuarioLivro>  {
    const account: UserAccount = JSON.parse(localStorage.getItem('account')) || {token: null};
    const headers = new Headers(this.headers);
    headers.append('Authorization', account.senha);

    account['livros'].push(livro);
    const usuarioLivro: UsuarioLivro = {id: account.id, livros: account['livros']};
    localStorage.setItem('account', JSON.stringify(account));

    return this.http.put(`${this.baseUrl}/usuarioLivro/${account.id}`, JSON.stringify(usuarioLivro), {headers: headers})
      .toPromise()
      .then(() => usuarioLivro)
      .catch(this.handleError);
  }

  getPontosUsuarios(): Promise<UsuarioPonto[]> {
    const account: UserAccount = JSON.parse(localStorage.getItem('account')) || {token: null};
    const headers = new Headers(this.headers);
    headers.append('Authorization', account.senha);
    return this.http.get(`${this.baseUrl}/usuarioPonto`, {headers: headers})
        .toPromise()
        .then(resp => {
          console.log(resp.json());
          return resp.json().data as UsuarioPonto[];
        }).catch(this.handleError);
  }

  getPontosUsuario(): Promise<UsuarioPonto> {
    const account: UserAccount = JSON.parse(localStorage.getItem('account')) || {token: null};
    const headers = new Headers(this.headers);
    headers.append('Authorization', account.senha);
    return this.http.get(`${this.baseUrl}/usuarioPonto/${account.id}`, {headers: headers})
      .toPromise()
      .then(resp => {
        console.log(resp.json());
        return resp.json().data as UsuarioPonto;
      }).catch(this.handleError);
  }

  updatePontosUsuario(livro: Livro): Promise<UsuarioPonto> {

    const account: UserAccount = JSON.parse(localStorage.getItem('account')) || {token: null};
    const headers = new Headers(this.headers);
    headers.append('Authorization', account.senha);

    return this.http.get(`${this.baseUrl}/usuarioPonto/${account.id}`, {headers: headers})
      .toPromise()
      .then(resp => {
        console.log(resp.json().data);
        let pontosCategoria = 1 + Math.floor(livro.paginas / 100);
        const pontos = resp.json().data.pontos;
        if (pontos.length > 0) {
          const ponto = pontos.find(p => p.categoria === livro.categoria);
          if (ponto) {
              pontosCategoria += ponto.valor;
          }
        }
        let categoria = null;
        if (account['pontos'] === undefined) {
          account['pontos'] = [];
        } else {
          categoria = account['pontos'].find(p => p.categoria === livro.categoria);
        }
        if (categoria) {
          categoria.valor = pontosCategoria;
        } else {
          account['pontos'].push({valor: pontosCategoria, categoria: livro.categoria});
        }
        localStorage.setItem('account', JSON.stringify(account));
        const usuarioPonto: UsuarioPonto = {id: account.id, name: account.name, pontos: account['pontos']};

        return this.http.put(`${this.baseUrl}/usuarioPonto/${account.id}`, JSON.stringify(usuarioPonto), {headers: headers})
          .toPromise()
          .then(() => usuarioPonto)
          .catch(this.handleError);
      }).catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('Erro ao buscar dados', error);
    return Promise.reject(error);
  }
}
