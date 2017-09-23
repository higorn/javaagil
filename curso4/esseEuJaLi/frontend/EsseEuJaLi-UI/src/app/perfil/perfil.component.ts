import { Component, OnInit } from '@angular/core';
import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { LivroService } from '../service/livro.service';
import { Observable } from 'rxjs/Observable';
import { UserAccount } from '../model/user-account';
import { Livro } from '../model/livro';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {
  datasource: DataSource<{valor: number, categoria: string, trofeus: string}>;
  displayedColumns = ['pontos', 'categoria', 'trofeus'];

  constructor(private service: LivroService) { }

  ngOnInit() {
    this.service.getPontosUsuario().then(usuarioPontos => {
      const account: UserAccount = JSON.parse(localStorage.getItem('account'));
      this.datasource = new ListDataSource(usuarioPontos.pontos.map(ponto => {
        return {valor: ponto.valor, categoria: ponto.categoria, trofeus: this.getTrofeus2(ponto.categoria, account['livros'])};
      }));
    })
  }

  private getTrofeus(ponto) {
    return ponto.valor >= 5 ? 'Leitor de ' + ponto.categoria + ' (' + Math.floor(ponto.valor / 5) + ')' : null;
  }

  private getTrofeus2(categoria: string, livros: Livro[]) {
    const qtdLivro = livros.filter(livro => livro.categoria === categoria).length;
    return qtdLivro >= 5 ? 'Leitor de ' + categoria + ' (' + Math.floor(qtdLivro / 5) + ')' : null;
  }
}

export class ListDataSource extends DataSource<{valor: number, categoria: string, trofeus: string}> {

  constructor(private data: {valor: number, categoria: string, trofeus: string}[]) {
    super();
  }

  connect(collectionViewer: CollectionViewer): Observable<{valor: number, categoria: string, trofeus: string}[]> {
    return Observable.of(this.data.sort((a, b) => b.valor - a.valor));
  }

  disconnect(collectionViewer: CollectionViewer): void {

  }
}

