# Plataforma de Gestão de Nutrição Animal

Sistema baseado em microsserviços para gestão de nutrição animal, desenvolvido com Java 21 + Spring Boot.

## Arquitetura

```
┌─────────────────────────┐        RabbitMQ (CloudAMQP)       ┌──────────────────────────────┐
│  feed-management-service │ ─────── fila: default.feeds ────► │  nutrition-analysis-service  │
│       porta: 8081        │                                    │         porta: 8083          │
│       PostgreSQL         │                                    │          MongoDB             │
└─────────────────────────┘                                    └──────────────────────────────┘
                                                                            │
                                                               REST GET /cost/{feedType}
                                                                            │
                                                               ┌────────────▼─────────┐
                                                               │   feed-cost-service  │
                                                               │      porta: 8082     │
                                                               │      PostgreSQL      │
                                                               └──────────────────────┘
```

### Fluxo completo

1. Cliente envia `POST /feeds` para o **feed-management-service**
2. Serviço salva o registro no PostgreSQL
3. Publica evento na fila RabbitMQ
4. **nutrition-analysis-service** consome o evento
5. Consulta o custo do insumo no **feed-cost-service** via REST
6. Calcula o custo total (`quantity * costPerKg`)
7. Salva a análise no MongoDB

---

## Pré-requisitos

- **Java 21+**
- **Maven 3.8+**
- **PostgreSQL** instalado localmente
- **Docker** (apenas para o MongoDB)
- **Git**

---

## Infraestrutura

### RabbitMQ

O projeto utiliza o **CloudAMQP** (RabbitMQ na nuvem). **Nenhuma instalação local é necessária** — a connection string já está configurada no `application.properties` de cada serviço.

### PostgreSQL (instalação local)

1. Certifique-se de que o PostgreSQL está rodando na porta padrão **5432**
2. Abra o terminal/prompt e acesse o `psql`:

```bash
psql -U postgres
```

3. Crie os dois bancos de dados:

```sql
CREATE DATABASE "feed-management";
CREATE DATABASE "feed-cost";
\q
```

> **Nota:** O usuário padrão configurado é `postgres` com senha `admin`. Se sua instalação usa outra senha, ajuste o arquivo `application.properties` dentro de `feed-management-service` e `feed-cost-service` (`src/main/resources/application.properties`).

### MongoDB (via Docker)

```bash
docker run -d --name mongodb -p 27017:27017 mongo:latest
```

> O banco `nutrition-analysis` e a collection são criados automaticamente pelo Spring na primeira execução.

---

## Como rodar

Clone o repositório:

```bash
git clone <url-do-repositorio>
cd bovexo-nutrition-platform
```

Abra **3 terminais** (um para cada serviço) e execute na ordem abaixo.

> **Importante:** O Maven precisa usar o **Java 21**. Se você tem múltiplas versões do Java instaladas, configure o `JAVA_HOME` antes de rodar os serviços:
> ```bash
> # Linux / Mac
> export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
>
> # Windows (CMD)
> set JAVA_HOME=C:\Program Files\Java\jdk-21
>
> # Windows (PowerShell)
> $env:JAVA_HOME="C:\Program Files\Java\jdk-21"
> ```
> Para verificar: `mvn -version` deve mostrar **Java version: 21**.

### 1. feed-cost-service (porta 8082)

> **Deve ser o primeiro a subir**, pois os outros serviços dependem dele.

```bash
cd feed-cost-service
mvn spring-boot:run
```

O script `data.sql` popula automaticamente a tabela `feed_cost` com os insumos e preços.

### 2. feed-management-service (porta 8081)

```bash
cd feed-management-service
mvn spring-boot:run
```

### 3. nutrition-analysis-service (porta 8083)

```bash
cd nutrition-analysis-service
mvn spring-boot:run
```

---

## Endpoints

### feed-management-service — `http://localhost:8081`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/feeds` | Registra consumo de ração |
| GET | `/feeds` | Lista todos os registros |
| GET | `/feeds/{animalId}` | Lista registros por animal |

**Exemplo POST /feeds:**
```json
{
  "animalId": 1,
  "feedType": "MILHO",
  "quantity": 10.0
}
```

**Tipos de ração disponíveis:**
`MILHO`, `SOJA`, `FARELO_SOJA`, `SORGO`, `TRIGO`, `SUPLEMENTO_MINERAL`, `NUCLEO_PROTEICO`, `SAL_BRANCO`, `UREIA`, `SILAGEM_MILHO`

---

### feed-cost-service — `http://localhost:8082`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/cost/{feedType}` | Retorna o custo por kg do insumo |

**Exemplo GET /cost/MILHO:**
```json
{
  "feedType": "MILHO",
  "costPerKg": 2.50
}
```

---

### nutrition-analysis-service — `http://localhost:8083`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/analysis` | Lista todas as análises |
| GET | `/analysis/{animalId}` | Lista análises por animal |

**Exemplo de resposta:**
```json
{
  "id": "6619a2f3e1b4c20012a3f001",
  "animalId": 1,
  "feedType": "MILHO",
  "quantity": 10.0,
  "costPerKg": 2.50,
  "totalCost": 25.00,
  "analysisDate": "2026-04-12T21:47:53"
}
```

---

## Tecnologias utilizadas

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| Java | 21 | Linguagem |
| Spring Boot | 3.x / 4.x | Framework |
| PostgreSQL | latest | Banco relacional |
| MongoDB | latest | Banco NoSQL |
| RabbitMQ | CloudAMQP | Mensageria assíncrona |
| Maven | 3.8+ | Gerenciamento de dependências |
