# Community Center API

API RESTful desenvolvida para gerenciar centros comunitários em situações de emergência, possibilitando o cadastro de centros, controle de recursos, realização de negociações entre centros e geração de relatórios operacionais.

---

## Requisitos Atendidos

Esta API foi desenvolvida para atender aos seguintes requisitos funcionais:

- Cadastro e listagem de centros comunitários.
- Registro e controle de recursos disponíveis (médicos, voluntários, veículos, etc.).
- Negociações de recursos entre centros, com regra de equivalência de pontos.
- Regra de flexibilização para centros com mais de 90% de ocupação.
- Registro de histórico de negociações.
- Relatórios:
  - Centros com ocupação acima de 90%.
  - Média de recursos por centro.
  - Histórico de negociações por centro e por intervalo de tempo.
- Testes unitários para a camada de serviço.
- API documentada com Swagger.
- Sistema dockerizado e pronto para execução em qualquer ambiente com Docker.

---

## Arquitetura

A aplicação foi desenvolvida seguindo princípios de arquitetura limpa e modular:

```
└── src/
    ├── controller/          → Camada de exposição de endpoints (REST)
    ├── service/             → Camada de lógica de negócio
    ├── repository/          → Camada de persistência (Spring Data JPA)
    ├── model/               → Entidades do domínio
    ├── dto/                 → Objetos de transferência de dados
    ├── util/                → Utilitários e mapeamentos
    └── exception/           → Exceções e manipuladores globais
```

---

## Conceitos Aplicados

- **Princípios SOLID**: especialmente SRP (responsabilidade única) e DIP (injeção de dependência).
- **DTOs com validação via Jakarta Bean Validation**.
- **Mapper isolado (`CentroMapper`)** para conversão entre entidades e DTOs.
- **Exceções personalizadas (`BusinessException`)** para regras de negócio.
- **Swagger (OpenAPI 3)** para documentação e testes de endpoints.
- **Testes unitários com JUnit 5 e Mockito**, garantindo segurança na lógica de negócio.
- **Separação de preocupações** bem definida entre as camadas.
- **Resiliência e validações defensivas** para evitar inconsistências de dados.

---

## Docker & Docker Compose

A aplicação foi totalmente dockerizada para facilitar o deploy e a execução local:

## Como Executar Localmente

### 1. Clonar o repositório

```bash
git clone git@github.com:IAbrahanArley/community_center.git
cd community-center
```

### 2. Compilar o projeto

```bash
./mvnw clean install
```

### 3. Subir com Docker Compose

```bash
docker-compose up --build
```

A API estará disponível em: `http://localhost:8080`

---

## Testes

Os **testes unitários** foram implementados na camada de serviço (`service`) para garantir:

- Validação de negociações válidas e inválidas.
- Testes de regra de flexibilização (alta ocupação).
- Cálculo correto da média de recursos por centro.
- Listagem de centros com alta ocupação.
- Cobertura de exceções personalizadas.

Executar testes com:

```bash
./mvnw test
```

---

## Documentação Swagger

Após subir a aplicação, acesse a interface interativa do Swagger:

[`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

Você poderá:

- Visualizar todos os endpoints.
- Realizar testes manuais com payloads prontos.
- Consultar regras de negócio diretamente nos campos da documentação.

---

## Tecnologias e Dependências

- Java 21
- Spring Boot 3.x
- Spring Data MongoDB
- Lombok
- Swagger / Springdoc OpenAPI
- Docker / Docker Compose
- JUnit 5 + Mockito

---

## Observações Finais

> Esta API foi desenvolvida com foco em legibilidade, boas práticas e testabilidade.
