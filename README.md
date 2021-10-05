# MAGALU CHALLENGE

Este sistema é responsável por manter Clientes e suas Listas de Desejos.

## Tech Stack

 - Kotlin Lang
 - Spring (Boot, Data JPA, Validation, Security)
 - Gradle
 - Postgres
 - Docker

## Tecnicalidades:

1. Por simplicidade optei por desenvolver o projeto usando com o padrão "Layered Architecture".
    - Controller --> Service --> Repository
2. O projeto possui testes unitários e testes de integração, em diferentes source folders.
    - Pode-se executar os testes de integração via gradle task **integrationTest**
    - Por breviedade, não foi desenvolvido testes para 100% de cobertura, mas apenas uma amostra de como eu faria no "mundo real".
3. O projeto conta com 1 enpoint autenticado e autorizado. "/top-secret"
    - User magalu/magalu possui permissão para acessa-lo
    - User not-magalu/not-magalu não possui permissão para acessa-lo

## Manual de instruções

O projeto está disponibilizado de maneira containerizada, é necessário ter o 
Docker e Docker-Compose instalados no seu computador para executa-lo.

Clone o projeto:
```
$ git clone https://github.com/FaveroFerreira/magalu-wishlist.git
```

Acesse a pasta do projeto:
```
$ cd magalu-wishlist
```

Execute o projeto:
```
$ docker-compose up
```

Após isso é possível acessar o Swagger do projeto pelo Link:

[Link do Swagger](http://localhost:8080/swagger-ui.html)

PS: a url http://localhost:8080 também faz redirect para a URL do swagger.

### Para adição de produtos:

É necessário enviar um ID de produto válido da API:

http://challenge-api.luizalabs.com/api/product/?page=1

## Throubleshooting

- É necessário ter as portas 8080 e 5432 disponíveis e livres no sistema para que seja possível
executar os containers sem conflitos de porta.

