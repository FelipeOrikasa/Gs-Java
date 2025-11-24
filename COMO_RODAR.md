# 🚀 Como Rodar o Projeto

## ⚡ Método Mais Fácil (Recomendado para começar)

### 1. Verificar Pré-requisitos

```bash
# Verificar Java (precisa ser 17 ou superior)
java -version

# Verificar Maven
mvn -version
```

**Se não tiver instalado:**
- **Java 17+**: Baixe em https://adoptium.net/ ou use `winget install EclipseAdoptium.Temurin.17.JDK`
- **Maven**: Baixe em https://maven.apache.org/ ou use `winget install Apache.Maven`

### 2. Rodar com H2 (Banco em Memória) - MAIS FÁCIL! ✅

**Não precisa instalar PostgreSQL nem RabbitMQ!**

```powershell
# Na pasta do projeto
cd C:\Users\Felipe\Downloads\Gs-Java

# Rodar com perfil dev (usa H2)
# IMPORTANTE: No PowerShell, use aspas no parâmetro -D
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

**Pronto!** A aplicação vai rodar em: **http://localhost:8080**

### 3. Acessar a Aplicação

- **Frontend Web**: http://localhost:8080
- **API REST**: http://localhost:8080/api
- **H2 Console** (banco de dados): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:workfuturedb`
  - Username: `sa`
  - Password: (deixe vazio)

### 4. Usuários Padrão

O sistema cria automaticamente estes usuários:

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ROLE_ADMIN |
| hruser | hr123 | ROLE_HR |
| user | user123 | ROLE_USER |

---

## 🔧 Método Alternativo (Com PostgreSQL)

Se quiser usar PostgreSQL ao invés de H2:

### 1. Instalar PostgreSQL

- Baixe em: https://www.postgresql.org/download/windows/
- Ou use: `winget install PostgreSQL.PostgreSQL`

### 2. Criar Banco de Dados

```sql
CREATE DATABASE workfuture_db;
```

### 3. Configurar Variáveis de Ambiente

**Windows PowerShell:**
```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="sua-senha"
$env:JWT_SECRET="sua-chave-secreta-minimo-32-caracteres-123456789012"
```

**Windows CMD:**
```cmd
set DB_USERNAME=postgres
set DB_PASSWORD=sua-senha
set JWT_SECRET=sua-chave-secreta-minimo-32-caracteres-123456789012
```

### 4. Rodar

```bash
mvn spring-boot:run
```

---

## 🎯 Testar a API

### 1. Fazer Login

**PowerShell:**
```powershell
curl.exe -X POST http://localhost:8080/api/auth/login `
  -H "Content-Type: application/json" `
  -d '{\"username\":\"admin\",\"password\":\"admin123\"}'
```

**CMD:**
```cmd
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

**Ou use Postman/Insomnia:**
- URL: `http://localhost:8080/api/auth/login`
- Method: `POST`
- Body (JSON):
```json
{
  "username": "admin",
  "password": "admin123"
}
```

### 2. Usar o Token

Copie o `token` da resposta e use nos próximos requests:

```powershell
curl.exe -X GET http://localhost:8080/api/skills `
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

---

## 🐛 Problemas Comuns

### Erro: "Java não encontrado"
```bash
# Instalar Java 17
winget install EclipseAdoptium.Temurin.17.JDK

# Verificar instalação
java -version
```

### Erro: "Maven não encontrado"
```bash
# Instalar Maven
winget install Apache.Maven

# Verificar instalação
mvn -version
```

### Erro: "Porta 8080 já em uso"
```bash
# Ver o que está usando a porta
netstat -ano | findstr :8080

# Ou mudar a porta no application.yml
# server.port: 8081
```

### Erro: "Build failed"
```bash
# Limpar e tentar novamente
mvn clean
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Aplicação não inicia
```bash
# Ver logs detalhados
mvn spring-boot:run -Dspring-boot.run.profiles=dev -X

# Ou verificar se todas as dependências foram baixadas
mvn dependency:resolve
```

---

## ✅ Checklist Rápido

- [ ] Java 17+ instalado (`java -version`)
- [ ] Maven instalado (`mvn -version`)
- [ ] Na pasta do projeto
- [ ] Executou: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
- [ ] Aplicação rodando em http://localhost:8080
- [ ] Consegue fazer login com `admin` / `admin123`

---

## 🎉 Pronto!

Agora você pode:
- ✅ Acessar http://localhost:8080
- ✅ Testar a API
- ✅ Gravar os vídeos mostrando o sistema funcionando
- ✅ Desenvolver e testar novas funcionalidades

**Dica:** Use o perfil `dev` (H2) para desenvolvimento rápido. Não precisa configurar nada!

