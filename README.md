# WebVagas {#top}

## Table of Contents {#sumary}
- [Overview](#overview)
- [Third party apps](#third-party)
- [Architecture](#architecture)
    - [Tecnical details](#detalhes-tecnicos)
    - [Stack tecnologies used](#tecnologies-stack)
    - [Dependencies](#dependencies)
- [Configuration](#configuration)
- [Usage](#usage)
- [Tests](#tests)
- [License](#license)

## Overview {#overview}
This is an application for scheduling WhatsApp message sending.
Its users will receive job listings containing opportunities that have been posted or reposted within the last 24 hours — aiming to increase hiring chances and reduce the need to sift through old or inactive listings that companies often leave open for long periods before suddenly closing.

Users can enjoy a **three-day free trial** of any plan with no charges, though they must **add a payment method** to activate the free trial.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## Third-Party Applications {#third-party-apps}
The application depends on three third-party services:

[Jsearch](https://rapidapi.com/letscrape-6bRBa3QguO5/api/jsearch) - A job search API used to return job postings based on supported filters.

[Stripe](https://stripe.com) - A payment API used to handle the checkout process, payment processing, role management, and customer dashboard.

[Twilio](https://www.twilio.com/en-us) - A messaging API used to send WhatsApp messages.

Note: My first choice was obviously Meta’s official API, but the approval process is rigorous and reserved for established companies.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

## Architecture {#architecture}
I chose a monolithic architecture with microservice elements, where the most complex logic is placed in a monolith to reduce the number of services and save time in the early stages of development.
The goal was to deliver a real, production-ready application within a strict deadline — and it was indeed tested in a real production environment.
However, due to the dissolution of a partnership, further development became unfeasible.
The long-term plan was to migrate to a full microservice architecture with observability.
All design choices were made to **maximize performance and minimize development time**, given that the project was built entirely by me.

- For more architectural details, see [Architecture](https://github.com/MarcoAntunes37/web-vagas/blob/main/doc/architecture.md)

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### Technical Details {#technical-details}
The application includes four microservices, as follows:

- [webvagas-api](https://github.com/MarcoAntunes37/web-vagas/tree/main/webvagas/webvagas-api) - Manages user job preferences and ensures duplicate job messages are not sent within the same period.

- [keycloak-server](https://github.com/MarcoAntunes37/web-vagas/blob/main/webvagas/docker-compose.dev.yml) - Manages users, tokens, roles, and access control.

- [admin-api](https://github.com/MarcoAntunes37/web-vagas/tree/main/webvagas/admin-api) - Handles scheduled message delivery, retrieving all required data to generate and send messages. It also includes a Stripe webhook to listen for payment events.

- [url-shortener](https://github.com/MarcoAntunes37/web-vagas/tree/main/webvagas/admin-api) - Shortens job listing URLs so they can be safely sent in WhatsApp messages without exceeding character limits (especially when URLs contain long query parameters).

- [gateway-api](https://github.com/MarcoAntunes37/web-vagas/tree/main/webvagas/gateway-api) - A gateway responsible for the application’s security layer, mainly protecting the webvagas and admin APIs, which require access control due to role management.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### Technologies used {#technologies}
Most of the application uses Java Spring Framework.
The only exception is the frontend, which is built with Angular.

I chose Spring for its predictability and JVM compatibility, and Angular for being the most complete web SPA framework I’ve worked with.

Other technologies include RabbitMQ, PostgreSQL, and Keycloak, among others.
For details, see [docker-compose.dev.yml](https://github.com/MarcoAntunes37/web-vagas/blob/main/webvagas/docker-compose.dev.yml) and [docker-compose.prod.yml](https://github.com/MarcoAntunes37/web-vagas/blob/main/webvagas/docker-compose.prod.yml).

Libraries used are listed in each project’s respective ```pom.xml``` file.

<div style="display: flex;flex-direction: column"> <a style="text-align: right;" href="#top">Back to top</a> </div>

### Dependencies {#dependencies}
Você precisa preencher alguns requisitos para conseguir rodar a aplicação em modo dev ou ate para buildar

- [Docker](https://docs.docker.com/desktop/setup/install/windows-install/) - Required in all environments.

- [Wsl](https://learn.microsoft.com/en-us/windows/wsl/install) - Required for Docker to work on Windows. Not needed on Linux systems.

- [OpenJDK 21](https://openjdk.org/) - Required for **build** and **dev** environments.

- [Maven](https://maven.apache.org/) - Required for **dev** and **build** environments.

<div style="display: flex;flex-direction: column"><a style="text-align: right;" href="#topo">Back to top</a></div>

## Configuration {#configuration}
Each project includes an example .env file that must be present when building or running.

Most configuration values work out of the box, but some (like API keys) need to be added manually.

The ```.env``` file is read according to the environment:

- .env.prod → production mode
- .env → dev mode

<div style="display: flex;flex-direction: column"><a style="text-align: right;" href="#topo">Back to top</a></div>

Usage {#uso}

The images must be built and available on Docker Hub under the Docker account logged into the production machine.

To ensure this, a **GitHub Actions workflow** is triggered automatically whenever a change occurs in the ```main``` branch.

First, make sure all configuration steps have been completed and that **all external API keys are valid**.
Additionally, the accounts that provide these keys must be properly configured, as described in their respective official documentations.

Next, make sure all containers are running and accessible.

With the Docker service active, run the following command from the project root:

> docker compose -f docker-compose.{environment}.yml up -d

The ```{environment}``` variable refers to the desired environment — either ```dev``` or ```prod```.

Once the containers are up and running, you’ll need to start each service (```webvagas```, ```admin```, ```gateway```, ```url```, and ```frontend```) if you’re in **development mode**.
In **production**, this step is unnecessary because a prebuilt image is generated for each microservice.

A **Keycloak setup** is also required to enable administrative and email confirmation services.

You must create a global **Keycloak administrator user** with the ```global admin``` role, using the same email that will be used for automatic emails (such as account confirmation).

Additionally, create a **realm administrator user** with the ```realm admin``` role — this allows the Stripe webhook to correctly assign roles to users.

After completing these steps, the user must access the web app and register a **preference**, since the admin-api **ignores users without a role or preference when sending messages**.

Once everything is properly configured and functional, at the scheduled time the application will:

- Retrieve all users and their roles,

- Apply specific handling for each role,

- Generate and send WhatsApp messages accordingly.

<div style="display: flex;flex-direction: column"><a style="text-align: right;" href="#topo">Back to top</a></div>

## Tests {#tests}
Run this command to execute unit tests:
> mvn test

Run this command to include integration tests:
> mvn verify

This repository includes a GitHub Actions pipeline that automatically executes all tests on every change outside ```main``` branch.

<div style="display: flex;flex-direction: column"><a style="text-align: right;" href="#topo">Back to top</a></div>

## License {#license}
> ⚠️ This project is intended for demonstration and educational purposes only.
> Commercial use, redistribution, or modification without permission is strictly prohibited.

<div style="display: flex;flex-direction: column"><a style="text-align: right;" href="#topo">Back to top</a></div>