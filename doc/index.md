# FlashVagas - Plataforma de Notificação de Vagas

## Visão Geral do Projeto

O **FlashVagas** é uma plataforma de notificação de vagas de emprego que envia oportunidades personalizadas diretamente para o WhatsApp dos usuários. O sistema utiliza APIs pagas de emprego para buscar vagas e o Twilio como ponte para entrega via WhatsApp.

## Objetivo Principal

Automatizar o processo de busca e notificação de vagas de emprego, entregando oportunidades personalizadas aos usuários através do WhatsApp, baseado em seus filtros de preferência e plano de assinatura.

## Modelo de Negócio

### Planos de Assinatura

#### Plano Start
- **Frequência**: 2 blocos de mensagem por dia
- **Horários**: 9h e 18h
- **Preço**: R$ 19,90/mês
- **Limite**: Até 5 vagas por bloco de mensagem

#### Plano Turbo
- **Frequência**: 4 blocos de mensagem por dia
- **Horários**: 7h, 12h, 17h e 20h
- **Preço**: R$ 29,90/mês
- **Limite**: Até 5 vagas por bloco de mensagem

### Estrutura das Mensagens
Cada bloco de mensagem contém:
- **Máximo**: 5 vagas
- **Formato**: Descrição da vaga + link direto
- **Personalização**: Baseada nos filtros configurados pelo usuário

## Fluxo do Usuário

### 1. Aquisição e Onboarding

```
Landing Page (Flutter) 
    ↓
Tela de Cadastro (Keycloak)
    ↓
Confirmação de Email
    ↓
Escolha do Plano
    ↓
Pagamento (Stripe)
    ↓
Login na Plataforma
    ↓
Configuração de Filtros
    ↓
Recebimento de Vagas (WhatsApp)
```

### 2. Detalhamento do Fluxo

#### **Landing Page (Flutter)**
- Apresentação dos planos e benefícios
- Call-to-action para assinatura
- Redirecionamento para cadastro

#### **Cadastro (Keycloak)**
- Coleta de dados pessoais
- Validação de email
- Criação de conta na plataforma
- Integração com sistema de autenticação

#### **Pagamento (Stripe)**
- Modal de pagamento integrado
- Processamento seguro de transação
- Confirmação de assinatura

#### **Configuração de Filtros**
- Interface Angular para definição de preferências
- Filtros por:
  - Localização
  - Tipo de contrato
  - Faixa salarial
  - Palavras-chave
  - Tipo de jornada
  - País

#### **Notificações (WhatsApp)**
- Envio automático via Twilio
- Horários programados por plano
- Conteúdo personalizado por usuário

## Arquitetura Técnica

### Stack Tecnológica

#### **Frontend**
- **Landing Page**: Flutter (melhorias necessárias)
- **Plataforma**: Angular 19.2.0
- **UI Framework**: Angular Material
- **Autenticação**: Keycloak Angular

#### **Backend**
- **APIs**: Spring Boot 3.5.0
- **Gateway**: Spring Cloud Gateway
- **Banco**: PostgreSQL 17
- **Mensageria**: RabbitMQ
- **Autenticação**: Keycloak 26.1

#### **Integrações**
- **Pagamentos**: Stripe
- **WhatsApp**: Twilio
- **Vagas**: APIs pagas de emprego
- **Email**: MailHog (dev) / SMTP (prod)

### Componentes do Sistema

#### **1. Landing Page (Flutter)**
- Interface de captação
- Apresentação de planos
- Redirecionamento para cadastro

#### **2. Plataforma Angular**
- Dashboard de usuário
- Configuração de filtros
- Gestão de preferências
- Interface de assinatura

#### **3. Gateway API**
- Roteamento de requisições
- Autenticação OAuth2
- Circuit breaker
- CORS configuration

#### **4. FlashVagas API**
- Gerenciamento de vagas
- Preferências de usuário
- Integração com APIs externas
- Processamento de mensagens

#### **5. Admin API**
- Agendamento de tarefas
- Envio de mensagens (Twilio)
- Processamento de pagamentos
- Webhooks

#### **6. Keycloak**
- Autenticação e autorização
- Gestão de usuários
- Single Sign-On
- Realm personalizado

## Funcionalidades Principais

### **Para o Usuário**
- Cadastro e autenticação
- Escolha de plano de assinatura
- Configuração de filtros de vagas
- Recebimento de notificações personalizadas
- Gestão de preferências

### **Para o Sistema**
- Agendamento automático de mensagens
- Integração com APIs de vagas
- Processamento de pagamentos
- Entrega via WhatsApp
- Monitoramento de performance

## Pontos de Melhoria Identificados

### **Frontend Angular**
- [ ] Melhorar responsividade
- [ ] Implementar lazy loading
- [ ] Otimizar performance
- [ ] Adicionar testes unitários
- [ ] Melhorar UX/UI

### **Landing Page Flutter**
- [ ] Refatorar código
- [ ] Melhorar design
- [ ] Otimizar conversão
- [ ] Implementar analytics

### **Backend**
- [ ] Implementar cache Redis
- [ ] Adicionar métricas Prometheus
- [ ] Melhorar logging
- [ ] Implementar rate limiting

## Configurações de Ambiente

### **Desenvolvimento**
- Docker Compose com todas as portas expostas
- MailHog para captura de emails
- Volumes persistentes para dados

### **Produção**
- SSL/TLS com Let's Encrypt
- Domínios configurados:
  - `flashvagas.com.br` (principal)
  - `auth.flashvagas.com.br` (Keycloak)
- Restart policies configuradas
- Nginx como proxy reverso

## Monitoramento e Observabilidade

### **Health Checks**
- Actuator endpoints em todas as APIs
- Circuit breaker no Gateway
- Monitoramento de agendadores

### **Logs e Métricas**
- Logs estruturados JSON
- Métricas Spring Boot Actuator
- Preparação para Prometheus

## Segurança

### **Autenticação**
- OAuth2/OIDC via Keycloak
- JWT tokens para APIs
- Silent SSO no frontend

### **Comunicação**
- HTTPS em produção
- CORS configurado
- Headers de segurança

## Roadmap Futuro

### **Curto Prazo**
- [ ] Implementar cache Redis
- [ ] Adicionar métricas Prometheus
- [ ] Melhorar logging estruturado
- [ ] Implementar rate limiting
- [ ] Adicionar health checks customizados

### **Médio Prazo**
- [ ] Implementar feature flags
- [ ] Adicionar testes de integração
- [ ] Implementar blue-green deployment
- [ ] Configurar monitoramento de agendadores
- [ ] Implementar retry policies para mensagens

### **Longo Prazo**
- [ ] Migração para Kubernetes
- [ ] Implementar service mesh
- [ ] Adicionar observabilidade distribuída
- [ ] Implementar multi-tenancy
- [ ] Adicionar suporte a múltiplos provedores de vagas

## Conclusão

O FlashVagas é uma solução completa para automatização de busca e notificação de vagas de emprego, oferecendo uma experiência personalizada aos usuários através de notificações WhatsApp. A arquitetura de microsserviços permite escalabilidade e manutenibilidade, enquanto as integrações com Stripe e Twilio garantem uma experiência de pagamento e entrega confiável.

O projeto está em evolução constante, com melhorias planejadas tanto no frontend quanto no backend, visando sempre a melhor experiência do usuário e a eficiência operacional do sistema.
