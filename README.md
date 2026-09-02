<h1 align="center">
  <br>
  Banco Digital API
  <br>
</h1>

<h4 align="center">Esse projeto expõe uma API REST que permite a transferência de valores entre contas e consulta de movimentações financeiras.</h4>

<p align="center">
  <a href="#tecnologias">Tecnologias</a> •
  <a href="#como-rodar">Como Rodar?</a> •
  <a href="#documentação">Documentação</a> •
  <a href="#decisões-arquiteturais">Decisões Arquiteturais</a> •
  <a href="#licença">Licença</a>
</p>


## Tecnologias

O projeto foi desenvolvido com as seguinte tecnologias:

* **Java 21**
* **Spring Boot 4.1.1**
* **Maven**
* **JUnit**
* **PostgreSQL**
* **Swagger/OpenAPI 3.0**
* **Docker**


## Como Rodar?

Para rodar a aplicação tenha certeza que tem instalado em sua linha de comando docker e docker-compose. Da sua linha de comando, rode:

```bash
# Clone o repositório
$ git clone https://github.com/anthonejeorge/banco-digital-api

# Entre na pasta
$ cd banco-digital-api

# Rode docker compose
$ docker-compose up --build
```

> **Note:**   
> A aplicação estará escutando na porta <b>8088</b>.

Para rodar a aplicação localmente em modo desenvolvimento na porta 8089, use o seguinte comando:

```bash
mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## Documentação

```bash
http://localhost:8088/swagger-ui/index.html
```

<h3>Recursos disponíveis:</h4>

<h5>Buscar todas as contas</h5>

```bash
GET /contas
```

<h5>Buscar uma conta por identificador</h5>

```bash
GET /contas/{contaId}
```

<h5>Criar uma conta</h5>

```bash
POST /contas
```

<h5>Adicionar saldo em uma conta</h5>

```bash
PATCH /contas/{contaId}/adicionar-saldo
```

<h5>Remover uma conta por identificador</h5>

```bash
DELETE /contas/{contaId}
```

<h5>Transferir saldo entre contas</h5>

```bash
POST /transferir
```

## Decisões Arquiteturais

Lista de decisões arquiteturais ordenadas por tempo (da mais recente para a mais antiga):

* Todos os recursos expostos via API REST deverão ter uma documentação correspondente com exemplos.
* A aplicação deverá prover idempotência para todos os recursos de API.
* A aplicação não deverá ser orientada à estado (statelessness) para entregar escalabilidade sem efeitos colaterais.
* Para garantir as propriedades ACID será utilizado o banco de dados PostgreSQL com o nível de isolamento de transação READ_COMMITTED (só realiza leituras e escritas de registros depois do commit) evitando dirty reads e dirty writes.
* Será utilizado o padrão arquitetural de software MVC baseado na maneira específica proposta pelo projeto Spring.

## Licença

MIT

