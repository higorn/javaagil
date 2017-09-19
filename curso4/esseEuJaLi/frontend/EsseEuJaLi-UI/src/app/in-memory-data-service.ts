import {InMemoryDbService} from 'angular-in-memory-web-api';
import {Livro} from './model/livro';
import { UserAccount } from './model/user-account';

export class InMemoryDataService implements InMemoryDbService {

    createDb(): {} {
        const account: UserAccount = [
            {id: '1', name: 'nicanor', displayName: 'Nicanor', token: 'abc'},
            {id: '2', name: 'tintones', displayName: 'Tintones', token: 'abc'},
            {id: '3', name: 'lauterio', displayName: 'Lauterio', token: 'abc'},
          {id: '4', name: 'cineide', displayName: 'Cineide', token: 'abc'},
          {id: '5', name: 'pimpolho', displayName: 'Pimpolho', token: 'abc'},
          {id: '6', name: 'jonelson', displayName: 'Jonelson', token: 'abc'},
          {id: '7', name: 'papael', displayName: 'Papael', token: 'abc'},
          {id: '8', name: 'dante', displayName: 'Dante', token: 'abc'},
          {id: '9', name: 'ramon', displayName: 'Ramon', token: 'abc'},
          {id: '10', name: 'ledesma', displayName: 'Ledsma', token: 'abc'},
        ];
        const usuarioLivro = [
          {id: '1', livros: []},
          {id: '2', livros: []},
          {id: '3', livros: []},
          {id: '4', livros: []},
          {id: '5', livros: []},
          {id: '6', livros: []},
          {id: '7', livros: []},
          {id: '8', livros: []},
          {id: '9', livros: []},
          {id: '10', livros: []},
        ];
        const usuarioPonto = [
          {id: '1', pontos: []},
          {id: '2', pontos: []},
          {id: '3', pontos: []},
          {id: '4', pontos: []},
          {id: '5', pontos: []},
          {id: '6', pontos: []},
          {id: '7', pontos: []},
          {id: '8', pontos: []},
          {id: '9', pontos: []},
          {id: '10', pontos: []},
        ];
        const livros: Livro[] = [
            {id: '1', titulo: 'Design Patterns com Java', subTitulo: 'Projeto orientado a objetos guuado por padrões',
                autor: 'Eduardo Guerra', editora: 'Casa do Código', categoria: 'Programação', paginas: 274, lido: false},
            {id: '2', titulo: 'Reflexão e Anotações', subTitulo: 'Componentes reutilizáveis em Java com',
                autor: 'Eduardo Guerra', editora: 'Casa do Código', categoria: 'Programação', paginas: 356, lido: false},
            {id: '3', titulo: 'Introduction to Neural Networks for Java', subTitulo: 'An artificial neural networks aproach ',
                autor: 'Jeff Heaton', editora: 'Heaton Research', categoria: 'IA', paginas: 439, lido: false},
        ];

        return {account, usuarioLivro, usuarioPonto, livros};
    }

}
