import {InMemoryDbService} from 'angular-in-memory-web-api';
import {Livro} from './model/livro';
import { UserAccount } from './model/user-account';

export class InMemoryDataService implements InMemoryDbService {

    createDb(): {} {
        const account: UserAccount = [
          {id: '1', name: 'nicanor', displayName: 'Nicanor', senha: 'abc'},
          {id: '2', name: 'tintones', displayName: 'Tintones', senha: 'abc'},
          {id: '3', name: 'lauterio', displayName: 'Lauterio', senha: 'abc'},
          {id: '4', name: 'cineide', displayName: 'Cineide', senha: 'abc'},
          {id: '5', name: 'pimpolho', displayName: 'Pimpolho', senha: 'abc'},
          {id: '6', name: 'jonelson', displayName: 'Jonelson', senha: 'abc'},
          {id: '7', name: 'papael', displayName: 'Papael', senha: 'abc'},
          {id: '8', name: 'dante', displayName: 'Dante', senha: 'abc'},
          {id: '9', name: 'ramon', displayName: 'Ramon', senha: 'abc'},
          {id: '10', name: 'ledesma', displayName: 'Ledsma', senha: 'abc'},
        ];
        const usuarioLivro = [
          {id: '1', livros: [{id: '2', categoria: 'Programação'}]},
          {id: '2', livros: [{id: '9', categoria: 'Ciência'}]},
          {id: '3', livros: [{id: '1', categoria: 'Programação'}]},
          {id: '4', livros: [{id: '5', categoria: 'Programação'}]},
          {id: '5', livros: [{id: '6', categoria: 'Programação'}]},
          {id: '6', livros: [{id: '13', categoria: 'Thriller de conspiração'}]},
          {id: '7', livros: [{id: '10', categoria: 'Ciência'}]},
          {id: '8', livros: [{id: '8', categoria: 'Ciência'}]},
          {id: '9', livros: [{id: '15', categoria: 'Thriller de conspiração'}]},
          {id: '10', livros: [{id: '18', categoria: 'Fábula'}]},
        ];
        const usuarioPonto = [
          {id: '1', name: 'nicanor', pontos: [{valor: 4, categoria: 'Programação'}]},
          {id: '2', name: 'tintones', pontos: [{valor: 5, categoria: 'Ciência'}]},
          {id: '3', name: 'lauterio', pontos: [{valor: 3, categoria: 'Programação'}]},
          {id: '4', name: 'cineide', pontos: [{valor: 3, categoria: 'Programação'}]},
          {id: '5', name: 'pimpolho', pontos: [{valor: 7, categoria: 'Programação'}]},
          {id: '6', name: 'jonelson', pontos: [{valor: 4, categoria: 'Thriller de conspiração'}]},
          {id: '7', name: 'papael', pontos: [{valor: 6, categoria: 'Ciência'}]},
          {id: '8', name: 'dante', pontos: [{valor: 3, categoria: 'Ciência'}]},
          {id: '9', name: 'ramon', pontos: [{valor: 4, categoria: 'Thriller de conspiração'}]},
          {id: '10', name: 'ledesma', pontos: [{valor: 1, categoria: 'Fabula'}]},
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
          {id: '5', titulo: 'JSF e JPA', subTitulo: 'Aplicações Java para web com',
            autor: 'Gilliard Cordeiro', editora: 'Casa do Código', categoria: 'Programação', paginas: 221, lido: false},
          {id: '6', titulo: 'Thinking in C++ Volume 1', subTitulo: '', autor: 'Bruce Eckel', editora: 'MindView Inc',
            categoria: 'Programação', paginas: 863, lido: false},
          {id: '7', titulo: 'Thinking in C++ Volume 2', subTitulo: '', autor: 'Bruce Eckel', editora: 'MindView Inc',
            categoria: 'Programação', paginas: 581, lido: false},
          {id: '8', titulo: 'As Conexões Ocultas', subTitulo: 'Ciência para uma vida sustentável',
            autor: 'Fritjof Capra', editora: 'Cultrix', categoria: 'Ciência', paginas: 296, lido: false},
          {id: '9', titulo: 'O Ponto de Mutação', subTitulo: '', autor: 'Fritjof Capra',
            editora: 'Digital Source', categoria: 'Ciência', paginas: 435, lido: false},
          {id: '10', titulo: 'Sabedoria Incomum', subTitulo: '', autor: 'Fritjof Capra',
            editora: 'Cultrix', categoria: 'Ciência', paginas: 560, lido: false},
          {id: '11', titulo: 'Inferno', subTitulo: '', autor: 'Dan Brown',
            editora: 'Arqueiro', categoria: 'Thriller de conspiração', paginas: 448, lido: false},
          {id: '12', titulo: 'O Código Da Vinci', subTitulo: '', autor: 'Dan Brown',
            editora: 'Arqueiro', categoria: 'Thriller de conspiração', paginas: 432, lido: false},
          {id: '13', titulo: 'O Ponto de Impácto', subTitulo: '', autor: 'Dan Brown',
            editora: 'Arqueiro', categoria: 'Thriller de conspiração', paginas: 332, lido: false},
          {id: '14', titulo: 'Fortaleza Digital', subTitulo: '', autor: 'Dan Brown',
            editora: 'Arqueiro', categoria: 'Thriller de conspiração', paginas: 372, lido: false},
          {id: '15', titulo: 'Anjos e Demônios', subTitulo: '', autor: 'Dan Brown',
            editora: 'Arqueiro', categoria: 'Thriller de conspiração', paginas: 392, lido: false},
          {id: '16', titulo: 'O Senhor dos Anéis', subTitulo: '', autor: 'J. R. R. Tolkien',
            editora: 'Martins Martins Fontes', categoria: 'Romance', paginas: 1234, lido: false},
          {id: '17', titulo: 'O Hobbit', subTitulo: '', autor: 'J. R. R. Tolkien', editora: 'Martins Martins Fontes',
            categoria: 'Romance', paginas: 376, lido: false},
          {id: '18', titulo: 'O Pequeno Príncipe', subTitulo: '', autor: 'Antoine de Saint-Exupéry',
            editora: 'Reynal & Hitchcock, Gallimard', categoria: 'Fábula', paginas: 94, lido: false},
        ];

        return {account, usuarioLivro, usuarioPonto, livros};
    }

}
