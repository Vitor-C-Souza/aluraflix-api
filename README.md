# Projeto de uma api para o Aluraflix
 gerenciar um site de videos separados por categoria.

## Descrição
Uma API RESTful contruída em Java para o aluraflix proposto pela alura para o challenger. Esta API permite salvar, procurar, atualizar e deletar videos do site e suas categorias com mesma função de criar categoria para organizar os videos apagar categoria e atualizar.
O sistema conta ainda por autenticação por Token JWT para segurança dos dados onde o usuario pode se cadastrar e usar os serviços da api como o sistema permitir.

## Índice
1. [Instalação](#instalação)
2. [Uso](#uso)
3. [Rotas da API](#rotas-da-api)
4. [Testes](#testes)
5. [Contribuição](#contribuição)

## Instalação
### Pré-requisitos
- Java 22+
- maven
- docker (opcional)
- mysql (opcional se não tiver docker instalado)

### Passos
1. Clone o repositório:
   ```bash
    git clone https://github.com/Vitor-C-Souza/aluraflix-api.git
    cd aluraflix-api
    ```

## Uso
Para iniciar o servidor execute o seguinte na raiz do projeto
1. Se tiver docker instalado:
    ```bash
    docker-compose up
    ```
2. Se não tiver docker instalado:
     ```bash
    mvn package
    java -jar target/*.jar
    ```

## Rotas da API
### POST /cadastro
```bash
    {
        "usuario": "vitor",
        "senha": "6040"
    } 
```

Cria um cadastro na API para poder se autenticar posteriormente

 - `usuario`(String): usuario que sera usado na hora de se autenticar.
 - `senha`(String): senha sera usada para se autenticar posteriormente. 

### POST /logar
#### body
```bash
    {
        "usuario": "vitor",
        "senha": "6040"
    } 
```

Roda para se autenticar e que tera um token JWT como retorno onde poderá ser usado no header das requisições.

 - `usuario`(String): usuario usado para se autenticar.
 - `senha`(String): senha do seu usuario para se autenticar.

#### retorno
```bash
    {
        "tokenJWT": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRvciIsImlzcyI6IkFQSSBhbHVyYWZsaXgiLCJleHAiOjE3MTYzMzM1NTZ9.oN3ooV0RKJBujragUC3j-SJCihWt3Y641BKvrWTUHjI"
    }
```

Exemplo de token JWT que pode ser usado até a expiração.

### GET /categoria
```bash
    {
        "content": [
            {
                "id": 12,
                "titulo": "musica",
                "cor": "verde"
            },
            {
                "id": 13,
                "titulo": "musica",
                "cor": "verde"
            },
            {
                "id": 14,
                "titulo": "musica",
                "cor": "verde"
            },
            {
                "id": 15,
                "titulo": "musica",
                "cor": "verde"
            },
            {
                "id": 16,
                "titulo": "musica",
                "cor": "verde"
            }
        ],
        "pageable": {
            "pageNumber": 2,
            "pageSize": 5,
            "sort": {
                "sorted": false,
                "empty": true,
                "unsorted": true
            },
            "offset": 10,
            "unpaged": false,
            "paged": true
        },
        "last": false,
        "totalPages": 4,
        "totalElements": 16,
        "size": 5,
        "number": 2,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "first": false,
        "numberOfElements": 5,
        "empty": false
    }
```

Retorna uma pagina de itens contendo 5 na primeira página onde pode ser enviado parametros por exemplo "?page=2" no final da url da requisição.

### GET /categorias/{id}
```bash
    {
        "id": 2,
        "titulo": "jogos",
        "cor": "vermelho"
    }
```
Se substituir o {id} por um salvo no banco por exemplo "2" retornara uma categoria salva.

### POST /categorias
#### body
```bash
    {
        "titulo": "musica",
        "cor": "verde"
    }
```
#### retorno
```bash
    {
        "id": 3,
        "titulo": "musica",
        "cor": "verde"
    }
```
Nesta rota criamos uma categoria e a salvamos no banco.

 - `titulo`(String): Este campo é o nome da categoria.
 - `cor`(String): Este campo sera para referenciar a cor que sera usada para representar a categoria.

### PUT /categorias/{id}
#### body
```bash
    rota /categorias/2

    {    
        "titulo": "jogos",
        "cor": "vermelho"
    }
```

#### retorna

```bash
    {
        "id": 2,
        "titulo": "jogos",
        "cor": "vermelho"
    }
```
Nesta rota atualizamos os dados de uma categoria enviando um titulo e uma cor e se algum desses dados tiver alguma diferença sera salvo essa nova versão no banco.

### DELETE /categorias/{id}

Substituindo o {id} por um id de uma categoria presente no banco ira apagar o dado salvo.

### GET /categorias/{id}/videos
Retorna todos os videos que possuem a categoria de no valor que colocar no lugar de {id} podendo até usar o paramentro `?page=1` para transitar entre as paginas trocando apenas o numero.

#### Exemplo /categorias/2/videos
```bash
    {
        "content": [
            {
                "id": 1,
                "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
                "descricao": "Analise da rodadda do MSI",
                "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog"
            },
            {
                "id": 2,
                "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
                "descricao": "Analise da rodadda do MSI",
                "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog"
            },
            {
                "id": 3,
                "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
                "descricao": "Analise da rodadda do MSI",
                "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog"
            },
            {
                "id": 4,
                "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
                "descricao": "Analise da rodadda do MSI",
                "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog"
            },
            {
                "id": 5,
                "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
                "descricao": "Analise da rodadda do MSI",
                "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog"
            }
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 5,
            "sort": {
                "sorted": false,
                "empty": true,
                "unsorted": true
            },
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
        "last": false,
        "totalPages": 4,
        "totalElements": 16,
        "size": 5,
        "number": 0,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 5,
        "empty": false
    }
```

### GET /videos
Retorna uma lista de videos paginadas contendo 5 itens por pagina.

```bash
    {
    "content": [
        {
        "id": 1,
        "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
        "descricao": "Analise da rodadda do MSI",
        "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
        "categoria_id": 2
        },
        {
        "id": 2,
        "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
        "descricao": "Analise da rodadda do MSI",
        "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
        "categoria_id": 2
        },
        {
        "id": 3,
        "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
        "descricao": "Analise da rodadda do MSI",
        "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
        "categoria_id": 2
        },
        {
        "id": 4,
        "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
        "descricao": "Analise da rodadda do MSI",
        "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
        "categoria_id": 2
        },
        {
        "id": 5,
        "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
        "descricao": "Analise da rodadda do MSI",
        "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
        "categoria_id": 2
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 5,
        "sort": {
        "sorted": false,
        "empty": true,
        "unsorted": true
        },
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "last": false,
    "totalPages": 4,
    "totalElements": 18,
    "size": 5,
    "number": 0,
    "sort": {
        "sorted": false,
        "empty": true,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 5,
    "empty": false
    }
```

### GET /videos/{id}

Se substituir o {id} pelo id de um video salvo no banco retornara este item salvo, exemplo de um de id 2.

```bash
    {
        "id": 2,
        "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
        "descricao": "Analise da rodadda do MSI",
        "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
        "categoria_id": 2
    }
```

### POST /videos
Salva um video no banco pelos dados fornecidos no corpo da requisição
```bash
    {
        "titulo": "titulo test",
        "descricao": "descrição teste",
        "url": "url teste",
        "categoria_id": 2
    }
```
#### Retorna
```bash
    {
        "id": 24,
        "titulo": "titulo test",
        "descricao": "descrição teste",
        "url": "url teste",
        "categoria": {
            "id": 2,
            "titulo": "jogos",
            "cor": "vermelho"
        }
    }
```
 - `id`(Int): identificação do video.
 - `titulo`(String): Este campo é o titulo do vídeo.
 - `descrição`(String): Este campo sera descrever o vídeo colocando dados se precisar.
 - `url`(String): Este campo é para informar a url do video.
 - `categoria_id`(Int): Este campo é para referenciar a categoria que localiza o video.

### PUT /videos/{id}
Atualiza o video do id indicado substituindo por um salvo no banco.
#### Body
```bash
    {    
        "titulo": "testando atualização do titulo",
        "descricao": "testando atualização da descrição",
        "url": "testando atualização do url",
        "categoria_id": 3
    }
```
#### Retorno
```bash
    {
        "id": 2,
        "titulo": "testando atualização do titulo",
        "descricao": "testando atualização da descrição",
        "url": "testando atualização do url",
        "categoria_id": 3
    }
```

### DELETE /videos/{id}
Apaga o video de uma determinada id que esta salva no banco de dados.

### GET /videos/?search={titulo}
Retorna uma pagina de itens com o 5 videos pelo nome ou parte dele se tiver salvo no banco, pesquisando pelo titulo do video.

Exemplo se passar um /videos/?search=teste e procurando no banco salvo no meu computador.

```bash
    {
        "content": [
            {
            "id": 18,
            "titulo": "teste titulo",
            "descricao": "teste descrição",
            "url": "teste url",
            "categoria_id": 2
            },
            {
            "id": 19,
            "titulo": "teste titulo",
            "descricao": "teste descrição",
            "url": "teste url",
            "categoria_id": 2
            },
            {
            "id": 22,
            "titulo": "teste titulo",
            "descricao": "teste descrição",
            "url": "teste url",
            "categoria_id": 2
            }
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 5,
            "sort": {
                "sorted": false,
                "empty": true,
                "unsorted": true
            },
                "offset": 0,
                "unpaged": false,
                "paged": true
        },
        "last": true,
        "totalPages": 1,
        "totalElements": 3,
        "size": 5,
        "number": 0,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
            "first": true,
            "numberOfElements": 3,
            "empty": false
    }
```

### GET /videos/free

Retorn 5 videos salvos esta rota não precisa de autenticação, pois é uma amostra de da api se estiver sendo consumida por um site por exemplo ter a opção de assistir alguns videos sem se logar.

```bash
    [
        {
            "id": 1,
            "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
            "descricao": "Analise da rodadda do MSI",
            "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
            "categoria_id": 2
        },
        {
            "id": 2,
            "titulo": "testando atualização do titulo",
            "descricao": "testando atualização da descrição",
            "url": "testando atualização do url",
            "categoria_id": 2
        },
        {
            "id": 3,
            "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
            "descricao": "Analise da rodadda do MSI",
            "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
            "categoria_id": 2
        },
        {
            "id": 4,
            "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
            "descricao": "Analise da rodadda do MSI",
            "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
            "categoria_id": 2
        },
        {
            "id": 5,
            "titulo": "G2 supreende e STOMPA a TES - MSI 2024",
            "descricao": "Analise da rodadda do MSI",
            "url": "https://www.youtube.com/watch?v=Vvy5G2Tjdog",
            "categoria_id": 2
        }
    ]
```

## Teste
Se quiser testar, mas não tem Postman instalado tem esta url que pode testar cada rota da api,
`http://localhost:8080/swagger-ui/index.html#/`.<br>
Obs.: Precisar ter iniciado o servidor para esta url funcionar.

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo:

1. Fork o repositório.
2. Crie uma nova branch (git checkout -b feature/nova-feature).
3. Commit suas alterações (git commit -m 'Adiciona nova feature').
4. Push para a branch (git push origin feature/nova-feature).
5. Abra um Pull Request.
