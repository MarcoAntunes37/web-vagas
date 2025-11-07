# WebVagas - Job Notification Platform {#top}

## Sumary
- [Overview](#overview)
  - [Main objective](#main-objective)
- [User flow](#user-flow)
  - [Acquisition and onboarding](#acquisition-and-onboarding)
  - [Flow details](#flow-details)
    - [Frontend](#flow-frontend)
    - [Register](#flow-register)
    - [Payment](#flow-payment)
    - [Configure user preferences](#flow-user-preferences)
    - [Notifications](#flow-notifications)
- [Technical architecture](#technical-architecture)
  - [Technology stack](#technology)
    - [Frontend](#tech-stack-frontend)
    - [Webvagas api](#tech-stack-webvagas)
    - [Urlshortener api](#tech-stack-urlshortener)
    - [Admin api](#tech-stack-admin)
    - [Gateway api](#tech-stack-gateway)
    - [Integrations](#tech-stack-integrations)
  - [System components responsabilities](#system-components-responsabilities)
    - [Frontend](#scr-frontend)
    - [Gateway API](#scr-gateway-api)
    - [WebVagas API](#scr-webvagas-api)
    - [Admin API](#admin-api)
    - [Urlshortener API](#urlshortener-api)
    - [Keycloak](#scr-keycloak)
- [Key features](#key-features)
  - [For users](#for-users)
  - [For system](#for-system)
- [Environment configuration](#environment-configuration)
  - [Development](#development)
  - [Production](#production)
- [Monitoring and observability](#monitoring-and-observability)
  - [Health checks](#health-checks)
  - [Logs and metrics](#logs-and-metrics)
- [Security](#security)
  - [Authentication](#authentication)
  - [Communication](#communication)
- [Conclusion](#conclusion)

## Overview {#overview}

**WebVagas** is a job notification platform that delivers personalized job opportunities directly to users’ WhatsApp. The system uses paid job APIs to fetch vacancies and Twilio as the bridge for WhatsApp message delivery.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### Main objective {#main-objective}

Automate the process of searching and notifying job openings, delivering personalized opportunities to users via WhatsApp based on their preference filters and subscription plan.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## User flow {#user-flow}

### 1. Acquisition and onboarding {#acquisition-and-onboarding}
<div style="overflow: auto;"> 
  <pre class="mermaid"> 
  flowchart TD 
    A[Access the application] --> B[Redirect to login screen] 
    B --> C{Does the user have an account?} 
    C -->|No| D[Click on register] 
    D --> E[Redirect to registration screen] 
    E --> F[Registration Form] 
    F --> G[Fill out and submit form] 
    G --> H[Receive confirmation email] 
    H --> I[Confirm email] 
    I --> J[Login] 
    C -->|Yes| J 
    J --> K{Does the user have an active subscription?} 
    K -->|No| L[Redirect to subscription page] 
    L --> M[Choose a Plan] 
    M --> N[Payment] 
    N --> O[Payment confirmation] 
    O --> P[Add role with user subscription] 
    P --> Q[Set up Filters] 
    Q --> R[Receive Job Notifications] 
    K -->|Yes| Q 
  <pre> 
</div>

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### 2. Flow details {#flow-details}
#### **Frontend(Angular)** {#flow-frontend}
- Redirects to the Keycloak realm login page.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Register (Keycloak)** {#flow-register}
- Collects personal data and creates user accounts.
- Validates email and terms agreement.
- Integrates with authentication system.
- Allows user self-management.
- Administrators can manage and impersonate users.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Payment (Stripe)** {#flow-payment}
- Api de pagamento do stripe gera uma tela de checkout.
- Processamento seguro de transação.
- Confirmação de assinatura.
- Captura dos eventos stripe pelo webhook.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Configure user preferences** {#flow-user-preferences}
- Angular interface for defining preferences.
- Filters include:
  - Keywords: A required search string.
  - EmploymentTypes: Available job types [EmploymentType](https://github.com/MarcoAntunes37/web-vagas/blob/main/webvagas/webvagas-api/src/main/java/com/webvagas/api/webvagas_api/domain/entity/user_preferences/enums/EmploymentType.java).
  - RemoteWork: Boolean flag indicating whether to show only remote jobs..
    - Country: Required search country.
    - ExcludeJobPublishers: String of publishers to exclude from job notifications.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Notifications (WhatsApp)** {#flow-notifications}
- Automatic delivery via Twilio.
- Scheduled message times based on subscription plan.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## Technical architecture {#technical-architecture}

### Technology stack {#technology}

#### **Frontend** {#tech-stack-frontend}
- **Plataforma**: Angular 19.2.0
- **UI Framework**: Angular Material
- **Authentication**: Keycloak Angular

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Webvagas api** {#tech-stack-webvagas}
- **APIs**: Spring Boot 3.5.0
- **Gateway**: Spring Cloud Gateway
- **Database**: PostgreSQL 17
- **Broker**: RabbitMQ
- **Authentication**: Keycloak 26.1

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Urlshortener api** {#tech-stack-urlshortener}
- **APIs**: Spring Boot 3.5.0
- **Gateway**: Spring Cloud Gateway
- **Database**: PostgreSQL 17

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Admin api** {#tech-stack-admin}
- **APIs**: Spring Boot 3.5.0
- **Gateway**: Spring Cloud Gateway

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Gateway api** {#tech-stack-gateway}
- **APIs**: Spring Boot 3.5.0
- **Gateway**: Spring Cloud Gateway
- **Aggregate route**: /api/v1/**

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **Integrations** {#tech-stack-integrations}
- **Pagamentos**: Stripe
- **WhatsApp**: Twilio
- **Vagas**: Jsearch
- **Email**: MailHog (dev) / SMTP (prod)

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### System components responsabilities {#system-components-responsabilities}

#### **1. Frontend** {#scr-frontend}
- User dashboard
- Filter configuration
- Preference management
- Subscription interface

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **2. Gateway API** {#scr-gateway-api}
- Request routing
- OAuth2 authentication
- Circuit breaker
- CORS configuration

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **3. WebVagas API** {#scr-webvagas-api}
- Job management
- User preference handling
- Integration with external APIs
- Message processing

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **4. Admin API** {#admin-api}
- Task scheduling
- Twilio message sending
- Payment processing
- Webhook handling

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **5. Urlshortener API** {#urlshortener-api}
- Generate a short url based on original url
- Part of message creation requeriments
- Jsearch urls can receive a lot of parameters or very big text via parameters and that can break message service
- Supply a controlled url value in message to avoid message surpass 4096 characters (Twilio message requeriment)

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

#### **6. Keycloak** {#scr-keycloak}
- Authentication and authorization
- User management
- Single Sign-On (SSO)
- Custom realm configuration

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## Key features {#key-features}

### **For users** {#for-users}
- Account registration and authentication
- Subscription plan selection
- Job filter configuration
- Personalized job notifications
- Preference management

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### **For system**  {#for-system}
- Automatic message scheduling
- Integration with job search APIs
- Payment processing
- WhatsApp delivery
- Performance monitoring

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## Environment configuration {#environment-configuration}

### **Development** {#development}
- Docker Compose with all ports exposed
- MailHog for email capture
- Persistent volumes for data

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### **Production** {#production}
- SSL/TLS via Cloudflare
- Configured domains:
  - webvagas.com.br (main)
  - auth.webvagas.com.br (Keycloak)
- Restart policies configured
- Nginx as reverse proxy

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## Monitoring and observability {#monitoring-and-observability}

### **Health checks** {#health-checks}
- Actuator endpoints in all APIs
- Circuit breaker at Gateway level
- Scheduler monitoring

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### **Logs and metrics** {#logs-and-metrics}
- Structured JSON logs
- Spring Boot Actuator metrics
- Prometheus-ready configuration

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## Security {#security}

### **Authentication** {#authentication}
- OAuth2/OIDC via Keycloak
- JWT tokens for APIs
- Silent SSO in frontend

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### **Communication** {#communication}
- HTTPS in production
- Proper CORS configuration
- Security headers enabled

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## Conclusion {#conclusion}

**WebVagas** is a complete solution for automating the search and delivery of job opportunities, providing users with a personalized experience through WhatsApp notifications.
Its microservices architecture enables scalability and maintainability, while integrations with **Stripe** and **Twilio** ensure secure payments and reliable message delivery.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>