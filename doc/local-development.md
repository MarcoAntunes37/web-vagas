# WebVagas - Local Development Guide

## Prerequisites

### **Required Software**
- **Docker & Docker Compose**: for infrastructure
- **Java 21**: for Spring Boot APIs
- **Node.js 18+**: for the Angular frontend
- **Maven**: for API builds
- **Git**: for version control

### **Recommended Versions**
```bash
# Check versions
java --version    # Java 21
node --version    # Node.js 18+
npm --version     # npm 9+
docker --version  # Docker 24+
mvn --version     # Maven 3.8+
```

## Initial Setup

### **1. Clone the Repository**
```bash
git clone <repository-url>
cd webvagasapp
```

### **2. Environment Variables**

Create a `.env` file in the project root based on the `.env.example` file.

### **3. API Configuration**

#### **Admin API**
Create `admin-api/.env` based on `admin-api/.env.example`.

#### **WebVagas API**
Create `webvagas-api/.env` based on `webvagas-api/.env.example`.

#### **Gateway API**
Create `gateway-api/.env` based on `gateway-api/.env.example`.

### **4. Frontend Configuration**
Create `frontend/.env.dev` based on `frontend/.env.example`.

## Running the Project

### **1. Start Infrastructure (Docker)**
```bash
# From the project root
docker-compose -f docker-compose.dev.yml --env-file .env up -d
```

**Containers that will be started:**
- PostgreSQL (backend): `localhost:5432`
- PostgreSQL (keycloak): `localhost:5433`
- Keycloak: `localhost:8181`
- RabbitMQ: `localhost:5672`
- RabbitMQ Management UI: `localhost:15672`
- MailHog: `localhost:1025` (SMTP) e `localhost:8025` (Web UI)

### **2. Verify Infrastructure**
```bash
# verify containers
docker ps

# verify logs
docker logs <container name>
docker logs <container id>
```

### **3. Run Spring Boot APIs**

#### **Admin API**
```bash
cd admin-api
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# API listen: http://localhost:8081
```

#### **WebVagas API**
```bash
cd webvagas-api
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# API listen: http://localhost:8080
```

#### **Gateway API**
```bash
cd gateway-api
mvn spring-boot:run -Dspring-boot.run.profiles=dev
# Gateway listen: http://localhost:9000
```

### **4. Run Angular Frontend**
```bash
cd frontend

# Install dependencies
npm install

# Update the prebuild script in package.json to APP_ENV=dev if not already
prebuild: "cross-env APP_ENV=dev ts-node builder/scripts/generate-env.ts",

# Generate environment.ts
npm run prebuild

# Start development server
ng serve
```

## Access and URLs

### **Applications**
- **Frontend**: http://localhost:4200
- **Gateway API**: http://localhost:9000
- **WebVagas API**: http://localhost:8080
- **Admin API**: http://localhost:8081

### **Ferramentas de Desenvolvimento**
- **Keycloak Admin**: http://localhost:8181
  - Username: `admin`
  - Password: `admin`
- **RabbitMQ Management UI**: http://localhost:15672
  - Username: `guest`
  - Password: `guest`
- **MailHog**: http://localhost:8025

### **API Documentation**
- **Gateway Swagger**: http://localhost:9000/swagger-ui.html
- **WebVagas API Swagger**: http://localhost:8080/swagger-ui.html
- **Admin API Swagger**: http://localhost:8081/swagger-ui.html

## Keycloak Configuration

### **1. Access Keycloak as Admin**
- URL: http://localhost:8181
- Username: `admin`
- Password: `admin`

### **2. Verify realm**
- The `WebVagas` realm should be automatically configured.
- Check if the `angular` client exists.

### **3. Verify client angular**
- **Client ID**: `angular`
- **Client Protocol**: `openid-connect`
- **Access Type**: `public`
- **Valid Redirect URIs**: `http://localhost:4200/*`
- **Web Origins**: `http://localhost:4200`

## System Tests

### **1. Register a new test user**
1. Access: http://localhost:4200.
2. Click in register.
3. Fill out register form.
4. Confirm email. If you are in dev mode you can do it in mailhog server.

### **2. Testing payment**
1. You will need to be loged in.
2. Chose your subscription.
3. If you are in dev mode, will need to setup a test webhook forwarding using stripe cli. stripe --forward-to http://localhost:8081/api/v1/webhook
4. Then you can do checkout normally and the event listener will send directly to webhook what trigger a action to a pre configured event.
5. If done correctly the user will have a new role in keycloak that refers to user plan. 
6. That's allow to access user preferences panel what will be cover in next step.
Obs.: When use cli, stripe generate a totally new api key to computer using cli and that way they can send events directly. But that can cause missmatch of keys since stripe expect to try that transaction with same cli key to have access to that events.

### **3. Filters configuration**
1. Access dashboard.
2. Choose edit user preferences.
4. Keywords and Country are required values. User without them will be ignored in scheduler.
3. Fill out the form whit your preferences.
4. If done correctly it allow you to receive whatsapp messages in scheduler.
Obs.: In Twillio test mode only the organization member phone can be used to send message and that no will enfore any meta rules since it is a sandbox mock. During production mode a need to configure a service, a sender and a template validated by meta to work togheter.

### **4. Testing scheduler**
- Scheduler will run this task every scheduled time.
- Admin api have a route that can be used to test `/api/v1/send-message` see more in information in swagger.

## Development

### **Hot Reload**
- **Frontend**: Changes reflect automatically.
- **APIs**: Use `mvn spring-boot:run` with `spring-boot-devtools`.

### **Debug**
- **Frontend**: Use browser devtools.
- **APIs**: Configure ide debugger or other debugger tools.