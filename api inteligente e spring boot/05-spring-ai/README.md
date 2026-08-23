# DIO Spring Boot - Final Project 05: Spring AI (budgeting)

## O que o projeto faz

Este projeto é uma API REST de controle financeiro simples, voltada para registrar e consultar despesas por categoria. A ideia principal é combinar arquitetura limpa com inteligência artificial, permitindo que o usuário faça operações por voz e também através de endpoints HTTP tradicionais.

O sistema permite:
- cadastrar transações financeiras;
- listar transações por categoria;
- processar áudio para interpretar comandos de gasto;
- transformar a resposta final em áudio usando texto para fala.

O fluxo principal da aplicação funciona assim:
1. O cliente envia um áudio com uma descrição de gasto.
2. A aplicação transcreve o áudio para texto.
3. O modelo AI identifica a ação correta e chama a ferramenta apropriada.
4. A aplicação persiste ou consulta uma transação.
5. A resposta final pode ser convertida em áudio e devolvida ao cliente.

## Como executar a aplicação

1. Configure sua chave da OpenAI:

```bash
export OPENAI_API_KEY="sua_chave_aqui"
```

2. Execute a aplicação:

```bash
./gradlew bootRun
```

3. A API ficará disponível em:

```text
http://localhost:8080
```

4. Para rodar os testes:

```bash
./gradlew test
```

## Qual melhoria você implementou

Melhorei os endpoints REST para deixá-los mais consistentes e profissionais do ponto de vista de API:

- a listagem passou a usar filtro por query parameter:
  - `GET /transactions?category=GROCERIES`
- o cadastro usa `POST /transactions` com validação do payload;
- o endpoint antigo por path variable foi mantido como suporte adicional;
- a API agora rejeita entradas inválidas com `400 Bad Request`;
- a estrutura de entrada da transação passou a validar valores obrigatórios e positivos.

Essa melhoria deixa a API mais previsível, fácil de consumir e mais próxima do padrão REST.

## Quais tecnologias foram usadas

- Java 25
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Hibernate / JPA
- MySQL
- Spring AI
- OpenAI API
- Gradle
- Docker Compose

## Como testar o fluxo principal

### 1. Cadastrar uma transação

```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Mercado",
    "category": "GROCERIES",
    "amount": 1250
  }'
```

### 2. Consultar transações por categoria

```bash
curl "http://localhost:8080/transactions?category=GROCERIES"
```

### 3. Testar o fluxo de IA com áudio

- envie um arquivo de áudio para:

```bash
POST /transactions/ai
```

- o sistema transcreve a gravação, interpreta o gasto e devolve um arquivo de áudio em MP3.

### 4. Testes automatizados

```bash
./gradlew test --tests "dio.budgeting.infrastructure.http.TransactionControllerTest"
```

## O que aprendi durante o desafio

Durante esse desafio, aprendi a importância de equilibrar arquitetura limpa com integrações inteligentes:

- a camada de domínio precisa continuar independente de frameworks e IA;
- os use cases são a ponte correta entre regras de negócio e interfaces externas;
- um endpoint REST bem projetado reduz ruído e facilita consumo por frontend, mobile e automações;
- Spring AI pode ser integrado sem quebrar a separação em camadas;
- validação e contrato de API são essenciais para evitar erros silenciosos e dados inconsistentes;
- testes de contrato ajudam a garantir que a API continue correta mesmo quando a funcionalidade evolui.

## Observações finais

Este projeto foi um exercício de aplicação prática de Spring AI e arquitetura de software, focando em produtividade e clareza de código. A combinação entre API tradicional e IA demonstra como tecnologias modernas podem coexistir com boas práticas de engenharia.
