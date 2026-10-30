# Notification Hub 🔔

API REST desenvolvida com **Java 21 e Spring Boot** para gerenciamento de notificações.

O Notification Hub é um projeto pessoal de backend criado para aprofundar conhecimentos em desenvolvimento de APIs, testes automatizados e, nas próximas etapas, persistência de dados, mensageria e serviços da AWS.

A proposta é evoluir a aplicação de forma incremental, até permitir o processamento assíncrono de notificações por diferentes canais.

## 🚀 Status do projeto

**MVP 1 — Concluído**

Atualmente, a aplicação permite cadastrar e consultar notificações, utilizando armazenamento em memória.

O projeto ainda **não realiza o envio efetivo de e-mails ou SMS**.

## 🛠️ Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Bean Validation
- Maven
- JUnit 5
- Mockito
- Git e GitHub

## ⚙️ Funcionalidades implementadas

- Cadastro de notificações por API REST
- Consulta de notificações por ID
- Suporte aos canais `EMAIL` e `SMS` no cadastro
- Geração automática de identificadores
- Status inicial `PENDING`
- Validação dos campos obrigatórios
- Tratamento de notificações não encontradas
- Armazenamento em memória
- Testes automatizados

## 📡 Endpoints

### Criar notificação

`POST /notifications`

Exemplo de requisição:

```json
{
  "recipient": "joao@teste.com",
  "message": "Minha primeira notificacao!",
  "channel": "EMAIL"
}
```

Resposta esperada — `201 Created`:

```json
{
  "id": 1,
  "recipient": "joao@teste.com",
  "message": "Minha primeira notificacao!",
  "channel": "EMAIL",
  "status": "PENDING"
}
```

### Consultar notificação

`GET /notifications/{id}`

Exemplo:

```http
GET /notifications/1
```

**Possíveis respostas:**

| Código HTTP | Descrição |
|---|---|
| `200 OK` | Notificação encontrada |
| `201 Created` | Notificação criada com sucesso |
| `400 Bad Request` | Dados inválidos na requisição |
| `404 Not Found` | Notificação não encontrada |

## 🏗️ Organização do projeto

A aplicação utiliza uma estrutura em camadas:

```text
src/main/java/com/notificationhub/notification_hub/
├── controller/
├── dto/
├── enumeracao/
├── exception/
├── model/
├── repository/
└── service/
```

**Responsabilidades:**

- **Controller:** recebe e responde às requisições HTTP.
- **DTO:** representa os dados recebidos pela API.
- **Service:** concentra as regras de negócio.
- **Repository:** gerencia o armazenamento das notificações.
- **Model:** representa as notificações.
- **Exception:** centraliza as exceções e o tratamento de erros.



## 🧪 Testes automatizados

O projeto possui testes utilizando **JUnit 5 e Mockito**, verificando comportamentos como:

- Criação de notificações com status `PENDING`
- Consulta de notificações existentes
- Tratamento de IDs inexistentes
- Criação de notificações pelo Service

Para executar os testes no Windows:

```powershell
.\mvnw.cmd test
```

No Linux/macOS:

```bash
./mvnw test
```

**Última execução validada do MVP 1:**

```text
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## 🗺️ Próximas evoluções

As próximas etapas previstas incluem:

- Persistência de dados com banco relacional
- Integração com mensageria utilizando Amazon SQS
- Processamento assíncrono de notificações
- Integração com serviços da AWS

Essas funcionalidades ainda não fazem parte do MVP 1.

## 📌 Observações

Atualmente, as notificações são armazenadas em memória. Portanto, os dados são perdidos quando a aplicação é reiniciada.

O projeto está em desenvolvimento e será atualizado conforme a conclusão dos próximos MVPs.