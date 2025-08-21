# FlashVagas - Guia de Desenvolvimento Local

## Pré-requisitos

### **Software Necessário**
- **Docker & Docker Compose**: Para infraestrutura
- **Java 21**: Para APIs Spring Boot
- **Node.js 18+**: Para frontend Angular
- **Maven**: Para build das APIs
- **Git**: Para controle de versão

### **Versões Recomendadas**
```bash
# Verificar versões
java --version    # Java 21
node --version    # Node.js 18+
npm --version     # npm 9+
docker --version  # Docker 24+
mvn --version     # Maven 3.8+
```

## Configuração Inicial

### **1. Clone do Repositório**
```bash
git clone <repository-url>
cd flashvagasapp
```

### **2. Configuração de Variáveis de Ambiente**

Crie um arquivo `.env` na raiz do projeto:

```bash
# Database Configuration
BACKEND_DB_HOST_PORT=5432
BACKEND_DB_CONTAINER_PORT=5432
BACKEND_DB_USERNAME=flashvagas
BACKEND_DB_PASSWORD=flashvagas123
BACKEND_DB_NAME=flashvagas

# Keycloak Configuration
KC_HOST_PORT=8181
KC_CONTAINER_PORT=8080
KC_DB_USERNAME=keycloak
KC_DB_PASSWORD=keycloak123
KC_DB_NAME=keycloak
KC_ADMIN_USERNAME=admin
KC_ADMIN_PASSWORD=admin

# Keycloak Database
KEYCLOAK_DB_HOST_PORT=5433
KEYCLOAK_DB_CONTAINER_PORT=5432
KEYCLOAK_DB_USER=keycloak
KEYCLOAK_DB_PASSWORD=keycloak123
KEYCLOAK_DB_NAME=keycloak

# RabbitMQ Configuration
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_HOST_PORT=5672
RABBITMQ_CONTAINER_PORT=5672
RABBITMQ_MANAGEMENT_HOST_PORT=15672
RABBITMQ_MANAGEMENT_CONTAINER_PORT=15672
RABBITMQ_DEFAULT_USER=guest
RABBITMQ_DEFAULT_PASS=guest

# MailHog Configuration
MAILHOG_SMTP_HOST_PORT=1025
MAILHOG_SMTP_CONTAINER_PORT=1025
MAILHOG_MANAGER_HOST_PORT=8025
MAILHOG_MANAGER_CONTAINER_PORT=8025
```

### **3. Configuração das APIs**

#### **Admin API**
Crie o arquivo `admin-api/.env.dev`:
```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/flashvagas
SPRING_DATASOURCE_USERNAME=flashvagas
SPRING_DATASOURCE_PASSWORD=flashvagas123

# RabbitMQ
SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest

# Keycloak
KEYCLOAK_URL=http://localhost:8181
KEYCLOAK_REALM=FlashVagas

# Stripe (para desenvolvimento)
STRIPE_SECRET_KEY=sk_test_...
STRIPE_WEBHOOK_SECRET=whsec_...

# Twilio (para desenvolvimento)
TWILIO_ACCOUNT_SID=AC...
TWILIO_AUTH_TOKEN=...
TWILIO_PHONE_NUMBER=+1234567890
```

#### **FlashVagas API**
Crie o arquivo `flashvagas-api/.env.dev`:
```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/flashvagas
SPRING_DATASOURCE_USERNAME=flashvagas
SPRING_DATASOURCE_PASSWORD=flashvagas123

# RabbitMQ
SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest

# Keycloak
KEYCLOAK_URL=http://localhost:8181
KEYCLOAK_REALM=FlashVagas
```

#### **Gateway API**
Crie o arquivo `gateway-api/.env.dev`:
```bash
# FlashVagas API URL
FLASHVAGAS_API_SERVICE_URL=http://localhost:8080

# Keycloak
KEYCLOAK_URL=http://localhost:8181
KEYCLOAK_REALM=FlashVagas
```

### **4. Configuração do Frontend**

Crie o arquivo `frontend/.env.dev`:
```bash
# API URLs
NG_APP_USER_PREFERENCES_API_URL=http://localhost:9000/api/v1/user-preferences
NG_APP_USER_PREFERENCES_JSEARCH_API_URL=http://localhost:9000/api/v1/user-preferences-jsearch
NG_APP_CHECKOUT_SESSION_API_URL=http://localhost:9000/api/v1/checkout-session

# Keycloak Configuration
NG_APP_KC_CONFIG_REALM=FlashVagas
NG_APP_KC_CONFIG_URL=http://localhost:8181
NG_APP_KC_CONFIG_CLIENT_ID=angular
```

## Executando o Projeto

### **1. Iniciar Infraestrutura (Docker)**
```bash
# Na raiz do projeto
docker-compose -f docker-compose.dev.yml up -d
```

**Serviços que serão iniciados:**
- PostgreSQL (backend): `localhost:5432`
- PostgreSQL (keycloak): `localhost:5433`
- Keycloak: `localhost:8181`
- RabbitMQ: `localhost:5672`
- RabbitMQ Management: `localhost:15672`
- MailHog: `localhost:1025` (SMTP) e `localhost:8025` (Web UI)

### **2. Verificar Infraestrutura**
```bash
# Verificar containers
docker-compose -f docker-compose.dev.yml ps

# Verificar logs
docker-compose -f docker-compose.dev.yml logs -f
```

### **3. Executar APIs Spring Boot**

#### **Admin API**
```bash
cd admin-api
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# API disponível em: http://localhost:8081
```

#### **FlashVagas API**
```bash
cd flashvagas-api
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# API disponível em: http://localhost:8080
```

#### **Gateway API**
```bash
cd gateway-api
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# Gateway disponível em: http://localhost:9000
```

### **4. Executar Frontend Angular**
```bash
cd frontend

# Instalar dependências
npm install

# Gerar environment.ts
npm run prebuild

# Iniciar servidor de desenvolvimento
npm start
# Frontend disponível em: http://localhost:4200
```

## Acessos e URLs

### **Aplicações**
- **Frontend**: http://localhost:4200
- **Gateway API**: http://localhost:9000
- **FlashVagas API**: http://localhost:8080
- **Admin API**: http://localhost:8081

### **Ferramentas de Desenvolvimento**
- **Keycloak Admin**: http://localhost:8181
  - Usuário: `admin`
  - Senha: `admin`
- **RabbitMQ Management**: http://localhost:15672
  - Usuário: `guest`
  - Senha: `guest`
- **MailHog**: http://localhost:8025

### **Documentação das APIs**
- **Gateway Swagger**: http://localhost:9000/swagger-ui.html
- **FlashVagas API Swagger**: http://localhost:8080/swagger-ui.html
- **Admin API Swagger**: http://localhost:8081/swagger-ui.html

## Configuração do Keycloak

### **1. Acessar Keycloak Admin**
- URL: http://localhost:8181
- Usuário: `admin`
- Senha: `admin`

### **2. Verificar Realm**
- O realm `FlashVagas` deve estar configurado automaticamente
- Verificar se o cliente `angular` está configurado

### **3. Configurações do Cliente Angular**
- **Client ID**: `angular`
- **Client Protocol**: `openid-connect`
- **Access Type**: `public`
- **Valid Redirect URIs**: `http://localhost:4200/*`
- **Web Origins**: `http://localhost:4200`

## Testando o Sistema

### **1. Criar Usuário de Teste**
1. Acesse: http://localhost:4200
2. Clique em "Cadastrar"
3. Preencha os dados
4. Confirme o email (verificar no MailHog)

### **2. Testar Fluxo de Pagamento**
1. Faça login na plataforma
2. Escolha um plano
3. Complete o pagamento (usar cartão de teste do Stripe)

### **3. Configurar Filtros**
1. Acesse o dashboard
2. Configure preferências de vagas
3. Salve as configurações

### **4. Testar Notificações**
- As notificações serão enviadas nos horários configurados
- Verificar logs das APIs para debug

## Troubleshooting

### **Problemas Comuns**

#### **Porta já em uso**
```bash
# Verificar processos usando a porta
netstat -ano | findstr :8080
# Windows
lsof -i :8080
# Linux/Mac

# Matar processo
kill -9 <PID>
```

#### **Docker não inicia**
```bash
# Parar todos os containers
docker-compose -f docker-compose.dev.yml down

# Remover volumes (cuidado: perde dados)
docker-compose -f docker-compose.dev.yml down -v

# Reconstruir
docker-compose -f docker-compose.dev.yml up -d --build
```

#### **APIs não conectam ao banco**
```bash
# Verificar se PostgreSQL está rodando
docker-compose -f docker-compose.dev.yml ps

# Verificar logs do PostgreSQL
docker-compose -f docker-compose.dev.yml logs backend-db
```

#### **Frontend não carrega**
```bash
# Limpar cache do npm
npm cache clean --force

# Remover node_modules e reinstalar
rm -rf node_modules package-lock.json
npm install
```

### **Logs Úteis**
```bash
# Logs de todos os serviços
docker-compose -f docker-compose.dev.yml logs -f

# Logs específicos
docker-compose -f docker-compose.dev.yml logs -f backend-db
docker-compose -f docker-compose.dev.yml logs -f keycloak
docker-compose -f docker-compose.dev.yml logs -f rabbitmq
```

## Desenvolvimento

### **Hot Reload**
- **Frontend**: Alterações são refletidas automaticamente
- **APIs**: Usar `mvn spring-boot:run` com `spring-boot-devtools`

### **Debug**
- **Frontend**: Usar DevTools do navegador
- **APIs**: Configurar debug na IDE (porta 5005)

### **Testes**
```bash
# Frontend
cd frontend
npm test

# APIs
cd admin-api
mvn test

cd flashvagas-api
mvn test

cd gateway-api
mvn test
```

## Parando o Projeto

```bash
# Parar frontend e APIs (Ctrl+C)

# Parar infraestrutura
docker-compose -f docker-compose.dev.yml down

# Parar e remover volumes (opcional)
docker-compose -f docker-compose.dev.yml down -v
```

## Próximos Passos

1. **Configurar Stripe**: Obter chaves de teste
2. **Configurar Twilio**: Obter credenciais de teste
3. **Configurar APIs de Vagas**: Integrar com provedores
4. **Testar Fluxo Completo**: Do cadastro até notificação

---

**Nota**: Este guia assume que você está rodando o projeto pela primeira vez. Para desenvolvimento contínuo, alguns passos podem ser pulados após a configuração inicial.
