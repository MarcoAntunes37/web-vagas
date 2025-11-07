# WebVagas - Job Notification Platform

Project Overview

**WebVagas** is a job notification platform that delivers personalized job opportunities directly to users’ WhatsApp. The system uses paid job APIs to fetch vacancies and Twilio as the bridge for WhatsApp message delivery.

## Main Objective

Automate the process of searching and notifying job openings, delivering personalized opportunities to users via WhatsApp based on their preference filters and subscription plan.

## User Flow

### 1. Acquisition and Onboarding
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

### 2. Flow Details
#### **Frontend(Angular)**
- Redirects to the Keycloak realm login page.

#### **Register (Keycloak)**
- Collects personal data and creates user accounts.
- Validates email and terms agreement.
- Integrates with authentication system.
- Allows user self-management.
- Administrators can manage and impersonate users.

#### **Payment (Stripe)**
- Api de pagamento do stripe gera uma tela de checkout.
- Processamento seguro de transação.
- Confirmação de assinatura.
- Captura dos eventos stripe pelo webhook.

#### **Configure UserPreferences**
- Angular interface for defining preferences.

- Filters include:
  - Keywords: A required search string.
  - EmploymentTypes: Available job types [EmploymentType](https://github.com/MarcoAntunes37/web-vagas/blob/main/webvagas/webvagas-api/src/main/java/com/webvagas/api/webvagas_api/domain/entity/user_preferences/enums/EmploymentType.java).
  - RemoteWork: Boolean flag indicating whether to show only remote jobs..
    - Country: Required search country.
    - ExcludeJobPublishers: String of publishers to exclude from job notifications.

#### **Notifications (WhatsApp)**
- Automatic delivery via Twilio.
- Scheduled message times based on subscription plan.

## Technical Architecture

### Technology Stack

#### **Frontend**
- **Plataforma**: Angular 19.2.0
- **UI Framework**: Angular Material
- **Autenticação**: Keycloak Angular

#### **Backend**
- **APIs**: Spring Boot 3.5.0
- **Gateway**: Spring Cloud Gateway
- **Banco**: PostgreSQL 17
- **Mensageria**: RabbitMQ
- **Autenticação**: Keycloak 26.1

#### **Integrations**
- **Pagamentos**: Stripe
- **WhatsApp**: Twilio
- **Vagas**: Jsearch
- **Email**: MailHog (dev) / SMTP (prod)

### System components

#### **1. Angular App**
- User dashboard
- Filter configuration
- Preference management
- Subscription interface

#### **2. Gateway API**
- Request routing
- OAuth2 authentication
- Circuit breaker
- CORS configuration

#### **3. WebVagas API**
- Job management
- User preference handling
- Integration with external APIs
- Message processing

#### **4. Admin API**
- Task scheduling
- Twilio message sending
- Payment processing
- Webhook handling

#### **5. Keycloak**
- Authentication and authorization
- User management
- Single Sign-On (SSO)
- Custom realm configuration

## Key Features

### **For Users**
- Account registration and authentication
- Subscription plan selection
- Job filter configuration
- Personalized job notifications
- Preference management

### **For the System**
- Automatic message scheduling
- Integration with job search APIs
- Payment processing
- WhatsApp delivery
- Performance monitoring

## Environment Configuration

### **Development**
- Docker Compose with all ports exposed
- MailHog for email capture
- Persistent volumes for data

### **Production**
- SSL/TLS via Cloudflare
- Configured domains:
  - webvagas.com.br (main)
  - auth.webvagas.com.br (Keycloak)
- Restart policies configured
- Nginx as reverse proxy

## Monitoring and Observability

### **Health Checks**
- Actuator endpoints in all APIs
- Circuit breaker at Gateway level
- Scheduler monitoring

### **Logs and metrics**
- Structured JSON logs
- Spring Boot Actuator metrics
- Prometheus-ready configuration

## Security

### **Authentication**
- OAuth2/OIDC via Keycloak
- JWT tokens for APIs
- Silent SSO in frontend

### **Communication**
- HTTPS in production
- Proper CORS configuration
- Security headers enabled

## Conclusion

**WebVagas** is a complete solution for automating the search and delivery of job opportunities, providing users with a personalized experience through WhatsApp notifications.
Its microservices architecture enables scalability and maintainability, while integrations with **Stripe** and **Twilio** ensure secure payments and reliable message delivery.