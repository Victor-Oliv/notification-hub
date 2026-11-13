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

| Status | Descrição |
|---|---|
| `PENDING` | Notificação criada e ainda não concluída |
| `SENT` | Mensagem aceita pelo servidor SMTP |
| `FAILED` | Falha durante a tentativa de envio |

**Importante:** o status `SENT` indica sucesso na operação SMTP, não confirmação de entrega na caixa de entrada final do destinatário.

## 🌐 Exemplo de utilização da API

### Criar uma notificação

**Método:** `POST`

**Endpoint:**

```http
POST http://localhost:8080/notifications
```

**Body:**

```json
{
  "recipient": "joao@teste.com",
  "message": "Teste real do MVP 3 - Notification Hub",
  "channel": "EMAIL"
}
```

### Exemplo de resposta — envio bem-sucedido

**HTTP 201 Created**

```json
{
  "recipient": "joao@teste.com",
  "message": "Teste real do MVP 3 - Notification Hub",
  "channel": "EMAIL",
  "id": 3,
  "status": "SENT"
}
```

### Exemplo de resposta — falha SMTP

**HTTP 201 Created**

```json
{
  "recipient": "joao@teste.com",
  "message": "Teste de falha SMTP - MVP 3",
  "channel": "EMAIL",
  "id": 4,
  "status": "FAILED"
}
```

### Comportamento HTTP

Atualmente, a API retorna `201 Created` quando o recurso de notificação é criado, mesmo que a tentativa de envio resulte em `FAILED`.

Isso ocorre porque a criação do recurso e o sucesso do envio são operações distintas.

O consumidor da API deve consultar o campo `status` para identificar o resultado da tentativa de envio.

## 🐳 Ambiente local

O ambiente de desenvolvimento utiliza Docker para executar PostgreSQL e Mailpit.

### PostgreSQL

Responsável pelo armazenamento das notificações e seus respectivos status.

### Mailpit

Utilizado para receber e visualizar e-mails durante o desenvolvimento, sem realizar entregas reais para destinatários externos.

**Configuração SMTP local:**

```properties
spring.mail.host=localhost
spring.mail.port=1025
```

**Interface web do Mailpit:**

```text
http://localhost:8025
```

As configurações do PostgreSQL e dos demais serviços devem corresponder ao ambiente local definido no projeto.

### Executando a aplicação

1. Inicie o Docker.
2. Inicie os serviços PostgreSQL e Mailpit conforme a configuração Docker do projeto.
3. Confirme que o banco de dados está disponível.
4. Inicie a aplicação Spring Boot.
5. Utilize o Postman ou outro cliente HTTP para enviar requisições.
6. Acesse o Mailpit para visualizar os e-mails enviados.

## 🧪 Testes automatizados

O projeto conta com testes unitários e de integração.

### Testes unitários

Utilizam **JUnit 5 e Mockito** para validar as regras de negócio sem depender de serviços externos.

Entre os cenários testados estão:

- Rejeição de canais de envio ainda não suportados
- Envio de notificações por meio do Provider
- Transição de status `PENDING → SENT`
- Transição de status `PENDING → FAILED`
- Tratamento de exceções durante o envio SMTP
- Verificação das chamadas ao repositório

### Testes de integração

Os testes de persistência utilizam **Testcontainers com PostgreSQL real**, permitindo validar operações de banco de dados e comportamento de transações.

O projeto também possui teste de integração do `EmailProvider` com o Mailpit, validando o envio SMTP.

### Executando os testes

**Linux/macOS:**

```bash
./mvnw clean test
```

**Windows (PowerShell):**

```powershell
.\mvnw.cmd clean test
```

Os testes que utilizam Testcontainers exigem Docker em execução.

O teste de integração do `EmailProvider` também exige que o Mailpit esteja disponível na configuração utilizada pelo projeto.

**Status:** suíte completa executada com sucesso após a implementação do MVP 3.

## ✅ Validação manual do MVP 3

Além dos testes automatizados, foram executados testes reais de ponta a ponta.

### Cenário 1 — Envio com sucesso

- Requisição `POST /notifications`
- Resposta HTTP `201 Created`
- Status retornado: `SENT`
- E-mail recebido pelo Mailpit
- Status `SENT` confirmado no PostgreSQL

**Resultado:** aprovado.

### Cenário 2 — Falha SMTP

- Mailpit temporariamente indisponível
- Requisição `POST /notifications`
- Resposta HTTP `201 Created`
- Status retornado: `FAILED`
- Status `FAILED` confirmado no PostgreSQL

**Resultado:** aprovado.

Esses cenários validam a integração entre API, Service, Provider, SMTP e banco de dados.

## 🗺️ Próximas etapas

- Integração com Amazon SQS
- Processamento assíncrono de notificações
- Evolução do suporte a outros canais, como SMS
- Integração com serviços AWS
- Implementação de estratégias de retry
- Evolução da observabilidade e do monitoramento
- Ampliação dos testes automatizados

## 📌 Observações

O projeto está em desenvolvimento contínuo e evolui por meio de MVPs.

Até o MVP 3, a aplicação oferece persistência em PostgreSQL e envio síncrono de notificações por e-mail via SMTP.

O envio por SMS ainda não está implementado.

O Mailpit é utilizado como ambiente local de testes de e-mail. A integração com serviços de entrega de e-mail em produção e o processamento assíncrono fazem parte da evolução futura do projeto.