# Guia de Configuração - Work Future Platform

## 🚀 Configuração Inicial

### 1. Banco de Dados

#### Opção A: PostgreSQL (Produção)
```bash
# Criar banco de dados
createdb workfuture_db

# Ou via SQL
CREATE DATABASE workfuture_db;
```

#### Opção B: H2 (Desenvolvimento)
O H2 é configurado automaticamente quando você usa o profile `dev`:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 2. RabbitMQ (Opcional para desenvolvimento)

#### Instalação Local
```bash
# Docker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# Ou via Homebrew (Mac)
brew install rabbitmq
brew services start rabbitmq
```

#### Acesso ao Management UI
- URL: http://localhost:15672
- Usuário: guest
- Senha: guest

**Nota:** Se o RabbitMQ não estiver disponível, o sistema continuará funcionando, mas as mensagens assíncronas não serão processadas.

### 3. Spring AI / OpenAI

#### Obter API Key
1. Acesse https://platform.openai.com/
2. Crie uma conta ou faça login
3. Vá em "API Keys" e crie uma nova chave
4. Configure no `application.yml` ou variável de ambiente:

```yaml
spring:
  ai:
    openai:
      api-key: sk-your-api-key-here
```

**Nota:** Se a API key não estiver configurada, o sistema usará análises automáticas como fallback.

### 4. Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```bash
# Database
DB_USERNAME=postgres
DB_PASSWORD=postgres
DATABASE_URL=jdbc:postgresql://localhost:5432/workfuture_db

# JWT
JWT_SECRET=your-256-bit-secret-key-for-jwt-token-generation-minimum-32-characters

# OpenAI
OPENAI_API_KEY=sk-your-api-key-here

# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USER=guest
RABBITMQ_PASS=guest
```

## 📦 Executando o Projeto

### Desenvolvimento (H2 Database)
```bash
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Produção (PostgreSQL)
```bash
mvn clean package
java -jar target/work-future-platform-1.0.0.jar --spring.profiles.active=prod
```

## 🔑 Usuários Padrão

O sistema cria automaticamente os seguintes usuários na primeira execução:

| Username | Password | Role | Email |
|----------|----------|------|-------|
| admin | admin123 | ROLE_ADMIN | admin@workfuture.com |
| hr | hr123 | ROLE_HR | hr@workfuture.com |
| user | user123 | ROLE_USER | user@workfuture.com |

## 🧪 Testando a API

### 1. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### 2. Criar Habilidade
```bash
curl -X POST http://localhost:8080/api/skills \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "name": "Spring AI",
    "description": "Inteligência Artificial com Spring",
    "category": "AI_ML",
    "level": "ADVANCED",
    "inDemand": true,
    "futureProof": true
  }'
```

### 3. Criar Caminho de Carreira
```bash
curl -X POST http://localhost:8080/api/career-paths \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "title": "Especialista em IA",
    "description": "Carreira em Inteligência Artificial",
    "type": "AI_SPECIALIST",
    "estimatedYears": 5,
    "averageSalary": 15000.0,
    "jobGrowth": 40,
    "futureCareer": true
  }'
```

### 4. Gerar Previsão de Carreira com IA
```bash
curl -X POST "http://localhost:8080/api/predictions/generate?userId=1&careerPathId=1&type=MEDIUM_TERM" \
  -H "Authorization: Bearer <TOKEN>"
```

## 🌐 Acessando o Frontend

Após iniciar o servidor, acesse:
- **URL:** http://localhost:8080/
- **Login:** Use uma das credenciais acima

## 🔧 Troubleshooting

### Erro: "ChatClient cannot be resolved"
- Verifique se a dependência do Spring AI está no `pom.xml`
- O sistema funciona sem Spring AI, usando análises automáticas

### Erro: "Connection refused" (RabbitMQ)
- Verifique se o RabbitMQ está rodando
- O sistema funciona sem RabbitMQ, mas mensagens assíncronas não serão processadas

### Erro: "Database connection failed"
- Verifique se o PostgreSQL está rodando
- Ou use o profile `dev` para usar H2 em memória

### Erro: "JWT Secret too short"
- Configure um JWT secret com pelo menos 32 caracteres
- Use: `openssl rand -base64 32` para gerar um secret seguro

## 📚 Recursos Adicionais

- [Documentação Spring Boot](https://spring.io/projects/spring-boot)
- [Documentação Spring AI](https://docs.spring.io/spring-ai/reference/)
- [Documentação RabbitMQ](https://www.rabbitmq.com/documentation.html)
- [Documentação PostgreSQL](https://www.postgresql.org/docs/)

