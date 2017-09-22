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
          {id: '1', name: 'nicanor', pontos: []},
          {id: '2', name: 'tintones', pontos: []},
          {id: '3', name: 'lauterio', pontos: []},
          {id: '4', name: 'cineide', pontos: []},
          {id: '5', name: 'pimpolho', pontos: []},
          {id: '6', name: 'jonelson', pontos: []},
          {id: '7', name: 'papael', pontos: []},
          {id: '8', name: 'dante', pontos: []},
          {id: '9', name: 'ramon', pontos: []},
          {id: '10', name: 'ledesma', pontos: []},
        ];
        const livros: Livro[] = [
          {id: '1', titulo: 'Design Patterns com Java', subTitulo: 'Projeto orientado a objetos guuado por padrões',
              autor: 'Eduardo Guerra', editora: 'Casa do Código', categoria: 'Programação', paginas: 274, lido: false},
          {id: '2', titulo: 'Reflexão e Anotações', subTitulo: 'Componentes reutilizáveis em Java com',
              autor: 'Eduardo Guerra', editora: 'Casa do Código', categoria: 'Programação', paginas: 356, lido: false},
          {id: '3', titulo: 'Introduction to Neural Networks for Java', subTitulo: 'An artificial neural networks aproach ',
              autor: 'Jeff Heaton', editora: 'Heaton Research', categoria: 'IA', paginas: 439, lido: false},
          {id: '4', titulo: 'Java 8 in Action', subTitulo: 'Lambdas, streams, and functional-style programming',
            autor: 'Raoul Gabriel Urma, Mario Fusco, Alan Mycroft', editora: 'Manning', categoria: 'Programação',
            paginas: 479, lido: false},
          {id: '5', titulo: 'As Conexões Ocultas', subTitulo: 'Ciência para uma vida sustentável',
            autor: 'Fritjof Capra', editora: 'Cultrix', categoria: 'Ciência', paginas: 296, lido: false},
          {id: '6', titulo: 'O Ponto de Mutação', subTitulo: '', autor: 'Fritjof Capra',
            editora: 'Digital Source', categoria: 'Ciência', paginas: 435, lido: false},
          {id: '7', titulo: 'Sabedoria Incomum', subTitulo: '', autor: 'Fritjof Capra',
            editora: 'Cultrix', categoria: 'Ciência', paginas: 560, lido: false},
          {id: '8', titulo: 'Inferno', subTitulo: '', autor: 'Dan Brown',
            editora: 'Arqueiro', categoria: 'Thriller de conspiração', paginas: 448, lido: false},
          {id: '9', titulo: 'O Código Da Vinci', subTitulo: '', autor: 'Dan Brown',
            editora: 'Arqueiro', categoria: 'Thriller de conspiração', paginas: 432, lido: false},
          {id: '10', titulo: 'O Senhor dos Anéis', subTitulo: '', autor: 'J. R. R. Tolkien',
            editora: 'Martins Martins Fontes', categoria: 'Romance', paginas: 1234, lido: false},
          {id: '11', titulo: 'O Hobbit', subTitulo: '', autor: 'J. R. R. Tolkien', editora: 'Martins Martins Fontes',
            categoria: 'Romance', paginas: 376, lido: false},
          {id: '12', titulo: 'O Pequeno Príncipe', subTitulo: '', autor: 'Antoine de Saint-Exupéry',
            editora: 'Reynal & Hitchcock, Gallimard', categoria: 'Fábula', paginas: 94, lido: false},
        ];

        return {account, usuarioLivro, usuarioPonto, livros};
    }

}
