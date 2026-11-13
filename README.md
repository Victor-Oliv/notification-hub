# 🔔 Notification Hub

API REST desenvolvida com **Java 21 e Spring Boot** para gerenciamento e envio de notificações.

O projeto faz parte da minha jornada de evolução como desenvolvedor backend, com foco em **Java, Spring Boot, testes automatizados, mensageria e AWS**.

A aplicação está sendo construída de forma incremental, por meio de MVPs, com o objetivo de evoluir de uma API REST simples para uma solução de processamento assíncrono de notificações.

Atualmente, o sistema permite cadastrar, consultar e enviar notificações por e-mail via SMTP, mantendo o histórico e o status das operações no PostgreSQL.

## 🚀 Evolução do projeto

### ✅ MVP 0 — Estrutura inicial

- Criação e configuração do projeto Spring Boot
- Organização inicial da aplicação
- Configuração do Git e GitHub

### ✅ MVP 1 — API REST

- Cadastro e consulta de notificações
- Definição dos canais `EMAIL` e `SMS`
- Validação de dados e tratamento de exceções
- Armazenamento inicial em memória
- Testes unitários com JUnit 5 e Mockito

### ✅ MVP 2 — Persistência e consultas

- Integração com PostgreSQL e Spring Data JPA
- Versionamento do banco de dados com Flyway
- Persistência de dados utilizando Docker
- Filtros por status e canal
- Paginação de resultados
- Testes de integração com Testcontainers
- Validação de transações com commit e rollback

### ✅ MVP 3 — Envio de notificações por e-mail

- Integração com Spring Mail para envio via SMTP
- Utilização do Mailpit como servidor SMTP local de testes
- Implementação da interface `NotificationProvider`
- Implementação do `EmailProvider`
- Gerenciamento dos status `PENDING`, `SENT` e `FAILED`
- Persistência do resultado do envio no PostgreSQL
- Tratamento de falhas SMTP com `MailException`
- Testes unitários com JUnit 5 e Mockito
- Teste de integração do envio real com Mailpit
- Validação do fluxo completo de sucesso e falha SMTP
- Execução da suíte completa de testes com sucesso

## 🛠️ Tecnologias

**Backend**
- Java 21
- Spring Boot 4.1.2
- Spring Web
- Spring Data JPA
- Spring Mail
- Bean Validation
- Maven

**Banco de dados**
- PostgreSQL 17
- Flyway
- Docker

**Testes**
- JUnit 5
- Mockito
- Testcontainers
- Mailpit

**Ferramentas**
- Git e GitHub
- Postman
- DBeaver

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas, com separação de responsabilidades entre Controller, Service, Repository e Provider.

Fluxo simplificado:

```text
                   HTTP Request
                        |
                        v
              NotificationController
                        |
                        v
               NotificationService
                        |
             +----------+----------+
             |                     |
             v                     v
    NotificationRepository   NotificationProvider
             |                     |
             v                     v
         PostgreSQL           EmailProvider
                                   |
                                   v
                              Spring Mail
                                   |
                                   v
                              Mailpit (SMTP)
```

### Responsabilidades

**Controller:** recebe as requisições HTTP e devolve as respostas da API.

**Service:** coordena as regras de negócio, a persistência e a tentativa de envio.

**Repository:** realiza as operações de persistência e consulta no PostgreSQL.

**NotificationProvider:** define o contrato para envio de notificações.

**EmailProvider:** implementa o envio de e-mails utilizando Spring Mail.

A separação entre Service e Provider permite evoluir o sistema para outros canais de notificação sem concentrar toda a lógica de envio na camada de serviço.

## 📩 Envio de notificações

O envio de e-mails é realizado de forma síncrona nesta etapa do projeto.

O fluxo de criação funciona da seguinte maneira:

1. A API recebe uma requisição para criar uma notificação.
2. A notificação é criada com status `PENDING`.
3. O registro inicial é persistido no PostgreSQL.
4. O Service solicita o envio ao `EmailProvider`.
5. O Provider utiliza Spring Mail para realizar o envio SMTP.
6. Se a operação SMTP for bem-sucedida, o status muda para `SENT`.
7. Se ocorrer uma `MailException`, o status muda para `FAILED`.
8. O status final é persistido no PostgreSQL.

### Status das notificações

| Status | Des