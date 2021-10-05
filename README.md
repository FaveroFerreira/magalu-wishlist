#MAGALU CHALLENGE

Este sistema é responsável por manter Clientes e suas Listas de Desejos.

##Tech Stack

 - Kotlin Lang
 - Spring (Boot, Data JPA, Validation, Security)
 - Gradle
 - Postgres
 - Docker

##Tecnicalidades:

Por simplicidade optei por desenvolver o projeto usando com o padrão "Layered Architecture".

``
    Controller --> Service --> Repository
``

O projeto está configurado para utilizar autenticação e autorização (InMemory/Basic),
porém, seguindo o solicitado, os endpoints relacionados as funcionalidades da challenge
irã́o permitir requests não autorizados.


##Manual de instruções

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

##Throubleshooting

- É necessário ter as portas 8080 e 5432 disponíveis e livres no sistema para que seja possível
executar os containers sem conflitos de porta.

- É possível que

