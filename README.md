# 🔔 Notification Hub

API REST desenvolvida com **Java 21 e Spring Boot** para gerenciamento de notificações.

O projeto faz parte da minha jornada de evolução como desenvolvedor backend, com foco em **Java, Spring Boot, testes automatizados, mensageria e AWS**.

A aplicação está sendo construída de forma incremental, por meio de MVPs, com o objetivo de evoluir de uma API REST simples para uma solução de processamento assíncrono de notificações.

## 🚀 Evolução do projeto

### ✅ MVP 0 — Estrutura inicial
- Criação e configuração do projeto Spring Boot
- Organização inicial da aplicação
- Configuração do Git e GitHub

### ✅ MVP 1 — API REST
- Cadastro e consulta de notificações
- Suporte aos canais `EMAIL` e `SMS`
- Validação de dados e tratamento de exceções
- Armazenamento inicial em memória
- Testes unitários com JUnit 5 e Mockito

### ✅ MVP 2 — Persistência e consultas
- Integração com PostgreSQL e Spring Data JPA
- Versionamento do banco com Flyway
- Persistência de dados utilizando Docker
- Filtros por status e canal
- Paginação de resultados
- Testes de integração com Testcontainers
- Validação de transações com commit e rollback

## 🛠️ Tecnologias

**Backend:** Java 21, Spring Boot 4.1.2, Spring Data JPA, Bean Validation, Maven

**Banco de dados:** PostgreSQL 17, Flyway, Docker

**Testes:** JUnit 5, Mockito, Testcontainers

**Ferramentas:** Git, GitHub, Postman e DBeaver

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

`Controller → Service → Repository → PostgreSQL`

A aplicação utiliza DTOs para entrada de dados, entidades JPA para persistência e tratamento centralizado de exceções.

## 🧪 Testes automatizados

O projeto conta com testes unitários e de integração.

Os testes de integração utilizam **Testcontainers com PostgreSQL real**, permitindo validar operações de persistência e comportamento de transações.

**Status:** suíte de testes executada com sucesso no MVP 2.

Para executar os testes:

```bash
./mvnw test
```

No Windows, utilize `.\mvnw.cmd test`.

Os testes de integração exigem que o Docker esteja em execução.

## 🗺️ Próximas etapas

- Integração com Amazon SQS
- Processamento assíncrono de notificações
- Integração com serviços AWS
- Evolução dos testes e da observabilidade

## 📌 Observações

Atualmente, a API cadastra e consulta notificações com persistência em PostgreSQL.

O envio real de e-mails e SMS ainda não foi implementado.

O projeto continuará evoluindo conforme a conclusão dos próximos MVPs.