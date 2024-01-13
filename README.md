# Projeto Teste Seguros Unimed API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

Este projeto é uma API construída usando Java, Java Spring,Database H2 como banco de dados e Spring Security e JWT para controle de autenticação.

## Sumário

- [Instalação](#Instalação)
- [Uso](#Uso)
- [Autenticação](#Autenticação)
- [Banco de Dados](#Banco de Dados)

## Instalação

1. Instale o JDK 11.
2. Instale as dependências com o Maven 3.
3. Clone o repositório:

```bash
git clone https://github.com/Eduardo-Silva-dev/segurosunimed-test
```

## Uso

1. Inicie a aplicação.
2. A API estará acessível em http://localhost:8080.
3. Para acessar documentação via swagger acesse o seguinte link - http://localhost:8080/swagger-ui/index.html

## Autenticação
A API utiliza o Spring Security para controle de autenticação. 
Os seguintes endpoints são publicos:
```bash
/auth/login - parametro email no body
```
Para acessar os demais endpoints, forneça as credenciais de autenticação apropriadas no cabeçalho da requisição.

## Banco de Dados
O projeto utiliza H2 como banco de dados. É inserido dados base no banco de dados.
