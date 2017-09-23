import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Location } from '@angular/common';

import 'rxjs/add/operator/switchMap';

import { Livro } from '../../model/livro';
import { LivroService } from '../../service/livro.service';
import { UserAccount } from '../../model/user-account';

@Component({
  selector: 'app-livro-detalhe',
  templateUrl: './livro-detalhe.component.html',
  styleUrls: ['./livro-detalhe.component.css']
})
export class LivroDetalheComponent implements OnInit {
  private account: UserAccount;
  livro: Livro = new Livro();

  constructor(
    private route: ActivatedRoute,
    private service: LivroService,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.service.getLivro(params.get('id')))
      .subscribe(livro => {
        this.account = JSON.parse(localStorage.getItem('account'));
        this.livro = livro
        this.livro.lido = this.account['livros'].map(l => l.id).indexOf(livro.id) > -1;
      });
  }

  onChange(): void {
    if (this.livro.lido) {
      this.service.updateLivrosUsuario(this.livro);
      this.service.updatePontosUsuario(this.livro);
    }
  }

  goBack(): void {
    this.location.back();
    console.log(Reflect.getMetadata('FormField', this.livro, 'titulo'));
  }

}
