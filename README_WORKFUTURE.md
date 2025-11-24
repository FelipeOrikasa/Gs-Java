# 🚀 Work Future Platform - O Futuro do Trabalho

Plataforma web completa para desenvolvimento profissional inteligente, utilizando Spring Framework, Spring AI e tecnologias modernas para preparar profissionais para o futuro do trabalho.

## 🎯 Tema do Projeto

**O Futuro do Trabalho** - Solução inovadora que utiliza Inteligência Artificial para:
- Análise preditiva de carreiras
- Recomendações de upskilling/reskilling
- Monitoramento de bem-estar no trabalho
- Previsão de carreiras do futuro
- Plataforma de treinamentos personalizados

## 🚀 Tecnologias Utilizadas

- **Spring Boot 3.2.0** - Framework principal
- **Spring Security** - Autenticação e autorização com JWT
- **Spring Data JPA** - Persistência de dados
- **Spring AI** - Inteligência Artificial Generativa para análises de carreira
- **RabbitMQ** - Mensageria assíncrona
- **PostgreSQL/H2** - Banco de dados
- **Caffeine Cache** - Cache em memória
- **Thymeleaf** - Template engine para frontend
- **Bean Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

## 📋 Requisitos Atendidos

✅ **Anotações Spring** - Configuração de beans e injeção de dependências  
✅ **Model/DTO** - Camada de modelo com métodos de acesso corretos  
✅ **Spring Data JPA** - Persistência de dados  
✅ **Bean Validation** - Validação de dados  
✅ **Caching** - Cache com Caffeine para melhor performance  
✅ **Internacionalização** - Suporte a Português (PT-BR) e Inglês (EN)  
✅ **Paginação** - Paginação para recursos com muitos registros  
✅ **Spring Security** - Autenticação JWT e autorização baseada em roles  
✅ **Tratamento de Erros** - GlobalExceptionHandler com mensagens internacionalizadas  
✅ **Mensageria** - Filas assíncronas com RabbitMQ  
✅ **Spring AI** - Recursos de IA Generativa para análises preditivas de carreira  
✅ **API REST** - Verbos HTTP adequados e códigos de status corretos  

## 🏗️ Arquitetura

```
src/main/java/com/globalsolution/workfuture/
├── config/              # Configurações (Security, Cache, RabbitMQ, i18n)
├── controller/          # Controllers REST
├── dto/                 # Data Transfer Objects
├── exception/           # Tratamento de exceções
├── model/               # Entidades JPA
├── repository/          # Repositórios Spring Data JPA
├── security/            # Componentes de segurança (JWT)
└── service/             # Lógica de negócio
    ├── ai/              # Serviços de IA (análise de carreira)
    └── messaging/       # Produtores e consumidores de mensagens
```

## 📊 Entidades do Domínio

1. **User** - Profissionais/Usuários com informações de carreira
2. **Skill** - Habilidades e competências (tecnológicas, soft skills, etc.)
3. **CareerPath** - Caminhos de carreira e profissões do futuro
4. **Training** - Treinamentos e cursos (upskilling/reskilling)
5. **Wellbeing** - Bem-estar e saúde mental no trabalho
6. **CareerPrediction** - Previsões de carreira geradas com IA

## 🔐 Segurança

- **Autenticação:** JWT (JSON Web Tokens)
- **Autorização:** Baseada em roles (RBAC)
  - `ROLE_USER` - Usuário comum
  - `ROLE_ADMIN` - Administrador
  - `ROLE_HR` - Recursos Humanos
  - `ROLE_MENTOR` - Mentor
- **Senhas:** Criptografadas com BCrypt

## 🚀 Funcionalidades Principais

1. **Gestão de Usuários** - CRUD completo com informações de carreira
2. **Gestão de Habilidades** - Cadastro e busca de skills (em alta demanda, do futuro)
3. **Caminhos de Carreira** - Carreiras do futuro com requisitos e projeções
4. **Treinamentos** - Plataforma de cursos e capacitação
5. **Bem-estar** - Monitoramento de saúde mental e satisfação no trabalho
6. **Previsões com IA** - Análises preditivas inteligentes de carreira
7. **Dashboard** - Visualização de métricas e recomendações

## 📡 Endpoints da API

### Autenticação
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registro de usuário

### Usuários
- `GET /api/users` - Listar usuários (ADMIN)
- `GET /api/users/{id}` - Obter usuário
- `PUT /api/users/{id}` - Atualizar usuário (ADMIN)
- `DELETE /api/users/{id}` - Deletar usuário (ADMIN)

### Habilidades
- `POST /api/skills` - Criar habilidade
- `GET /api/skills` - Listar habilidades (paginado)
- `GET /api/skills/in-demand` - Habilidades em alta demanda
- `GET /api/skills/future-proof` - Habilidades do futuro
- `GET /api/skills/{id}` - Obter habilidade
- `PUT /api/skills/{id}` - Atualizar habilidade
- `DELETE /api/skills/{id}` - Deletar habilidade

### Caminhos de Carreira
- `POST /api/career-paths` - Criar caminho de carreira
- `GET /api/career-paths` - Listar caminhos (paginado)
- `GET /api/career-paths/future` - Carreiras do futuro
- `GET /api/career-paths/{id}` - Obter caminho
- `PUT /api/career-paths/{id}` - Atualizar caminho
- `DELETE /api/career-paths/{id}` - Deletar caminho

### Treinamentos
- `POST /api/trainings` - Criar treinamento
- `GET /api/trainings` - Listar treinamentos (paginado)
- `GET /api/trainings/free` - Treinamentos gratuitos
- `GET /api/trainings/skill/{skillId}` - Treinamentos por habilidade
- `GET /api/trainings/career-path/{careerPathId}` - Treinamentos por carreira

### Bem-estar
- `POST /api/wellbeing` - Registrar bem-estar
- `GET /api/wellbeing/user/{userId}` - Histórico de bem-estar (paginado)
- `GET /api/wellbeing/user/{userId}/average-mental-health` - Média de saúde mental

### Previsões de Carreira (IA)
- `POST /api/predictions/generate` - Gerar previsão com IA
- `GET /api/predictions/{id}` - Obter previsão
- `GET /api/predictions/user/{userId}` - Previsões por usuário
- `GET /api/predictions/range` - Previsões por período

## 🔧 Configuração e Execução

### Pré-requisitos
- Java 17+
- Maven 3.6+
- PostgreSQL (ou usar H2 para desenvolvimento)
- RabbitMQ (opcional para desenvolvimento local)

### Variáveis de Ambiente

```bash
# Database
DB_USERNAME=postgres
DB_PASSWORD=postgres
DATABASE_URL=jdbc:postgresql://localhost:5432/workfuture_db

# JWT
JWT_SECRET=your-256-bit-secret-key-for-jwt-token-generation-minimum-32-characters

# OpenAI (Spring AI)
OPENAI_API_KEY=your-openai-api-key

# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USER=guest
RABBITMQ_PASS=guest
```

### Executar em Desenvolvimento

```bash
# Usando H2 (banco em memória)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Ou usando PostgreSQL
mvn spring-boot:run
```

### Executar em Produção

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
  -H "Authorization: Bearer <token>" \
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
  -H "Authorization: Bearer <token>" \
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

### 4. Registrar Bem-estar
```bash
curl -X POST http://localhost:8080/api/wellbeing \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "userId": 1,
    "stressLevel": 5,
    "workLifeBalance": 7,
    "jobSatisfaction": 8,
    "mentalHealthScore": 7,
    "workHours": 40,
    "isRemote": true
  }'
```

### 5. Gerar Previsão de Carreira com IA
```bash
curl -X POST "http://localhost:8080/api/predictions/generate?userId=1&careerPathId=1&type=MEDIUM_TERM" \
  -H "Authorization: Bearer <token>"
```

## 🌍 Internacionalização

O sistema suporta dois idiomas:
- **Português (pt-BR)** - Padrão
- **Inglês (en)** - Default

Para alterar o idioma, use o parâmetro `lang` na requisição:
```
GET /api/skills?lang=en
```

## 🤖 Spring AI

O sistema utiliza Spring AI para gerar análises preditivas inteligentes sobre carreiras. As análises incluem:
- Compatibilidade do profissional com a carreira
- Recomendações de upskilling/reskilling
- Potencial de crescimento e oportunidades
- Plano de ação para transição
- Habilidades prioritárias a desenvolver
- Riscos e desafios

## 📊 Mensageria

O sistema utiliza RabbitMQ para processamento assíncrono de:
- Registros de treinamentos
- Dados de bem-estar
- Geração de previsões
- Notificações

## 🚀 Deploy em Nuvem

### Opções de Deploy

1. **Heroku**
   ```bash
   heroku create work-future-platform
   heroku addons:create heroku-postgresql
   heroku config:set OPENAI_API_KEY=your-key
   git push heroku main
   ```

2. **AWS Elastic Beanstalk**
   - Configure as variáveis de ambiente no console AWS
   - Faça deploy do JAR gerado

3. **Google Cloud Run**
   ```bash
   gcloud run deploy work-future-platform --source .
   ```

4. **Azure App Service**
   - Configure as variáveis de ambiente no portal Azure
   - Faça deploy via Maven ou Azure CLI

## 📝 Exemplo de Uso Completo

### Fluxo Típico:

1. **Login**
2. **Cadastrar Habilidades** atuais
3. **Explorar Carreiras do Futuro**
4. **Gerar Previsão com IA** para uma carreira
5. **Ver Recomendações** de upskilling
6. **Buscar Treinamentos** recomendados
7. **Monitorar Bem-estar** no trabalho

## 🧪 Testes

```bash
mvn test
```

## 📄 Licença

Este projeto foi desenvolvido para o curso Java Advanced - Global Solution 2025.

## 👥 Autores

Desenvolvido como projeto acadêmico.

## 📞 Suporte

Para dúvidas ou problemas, consulte a documentação da API ou entre em contato com a equipe de desenvolvimento.

