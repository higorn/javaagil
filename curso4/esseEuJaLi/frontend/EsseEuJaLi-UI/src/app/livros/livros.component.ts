import { Component, OnInit } from '@angular/core';
import { Livro } from '../model/livro';
import { LivroService } from '../service/livro.service';
import { UsuarioLivro } from '../model/usuario-livro';
import { UserAccount } from '../model/user-account';

@Component({
  selector: 'app-livros',
  templateUrl: './livros.component.html',
  styleUrls: ['./livros.component.css']
})
export class LivrosComponent implements OnInit {

  livros: Livro[];

  constructor(private service: LivroService) { }

  ngOnInit() {
    this.getLivros();
  }

  getLivros(): void {
    this.service.getLivros().then(livros => {
      this.livros = livros
      this.service.getLivrosUsuario().then(usuarioLivro => {
        const account: UserAccount = JSON.parse(localStorage.getItem('account'));
        account['livros'] = usuarioLivro.livros;
        this.livros.filter(livro => usuarioLivro.livros.map(l => l['id']).indexOf(livro.id) > -1)
          .forEach(livro => livro.lido = true);

        this.service.getPontosUsuario().then(pontos => {
          account['pontos'] = pontos.pontos;
          localStorage.setItem('account', JSON.stringify(account));
        })
      });
    });
  }
}
