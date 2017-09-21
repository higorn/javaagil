import { Component, OnInit } from '@angular/core';
import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import { LivroService } from '../service/livro.service';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {
  datasource: DataSource<{name: string, pontos: number}>;
  displayedColumns = ['name', 'pontos'];

  constructor(private service: LivroService) { }

  ngOnInit() {
    this.service.getPontosUsuario().then(pontos => {
      console.log(pontos);
      this.datasource = new ListDataSource(pontos.map(ponto => {
        return {name: ponto.name, pontos: ponto.pontos.reduce((val, p) => val + p.valor, 0)}
      }));
    })
  }

}

export class ListDataSource extends DataSource<{name: string, pontos: number}> {

  constructor(private data: {name: string, pontos: number}[]) {
    super();
  }

  connect(collectionViewer: CollectionViewer): Observable<{name: string, pontos: number}[]> {
    return Observable.of(this.data);
  }

  disconnect(collectionViewer: CollectionViewer): void {

  }
}
