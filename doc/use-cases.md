## Use Cases Diagram

### Summary

- [Keycloak diagrams](#keycloak)
- [Stripe diagrams](#stripe)
- [Web application diagrams](#web)
- [Admin API diagrams](#admin-api)

### Keycloak diagrams {#keycloak}
<div style="overflow: auto;"> 
    <pre class="mermaid"> 
        zenuml title Keycloak user registration
            @Actor User
            Frontend
            Keycloak
            @Database KeycloakDB

            User -> Frontend : Fills out the form and submits it.
            Frontend -> Keycloak : Calls Keycloak authentication service (OIDC).
            Keycloak -> KeycloakDB : Creates a new user requiring email verification.
            KeycloakDB -> Keycloak : Confirms user creation success.
            Keycloak -> User : Sends verification email.

            User -> Keycloak : Clicks on the verification link.
            Keycloak -> KeycloakDB : Updates user as email verified.
            Keycloak -> User : Confirms account activation.
            Keycloak -> Frontend : Redirects to protected webapp routes.
    </pre>
</div>
<div style="overflow: auto;"> 
    <pre class="mermaid"> 
    zenuml 
        title Login with Keycloak
        @Actor User
        Frontend
        Keycloak
        @Database KeycloakDB

        User -> Frontend : Accesses a protected area.
        Frontend -> Keycloak : Redirects to login screen (OIDC).
        User -> Keycloak : Enters username/email and password, then submits.
        Keycloak -> KeycloakDB : Validates credentials.
        KeycloakDB -> Keycloak : Returns confirmation.
        Keycloak -> Frontend : Redirects with authorization code.
        Frontend -> Keycloak : Exchanges code for access_token (JWT).
        Keycloak -> Frontend : Returns access_token.
        Frontend -> User : Grants access.
    </pre>
</div>
<div style="overflow: auto;" id="kc-role-attribuition"> 
    <pre class="mermaid"> 
    zenuml title Role assignment in Keycloak
        StripeWebhookHandler
        KeycloakAdminRestApi
        Keycloak
        StripeApi

        StripeApi -> StripeWebhookHandler : Sends payment event.
        StripeWebhookHandler -> KeycloakAdminRestApi : Validates event signature and assigns role.
        KeycloakAdminRestApi -> Keycloak : Sends REST request to assign role by realm userId.
        Keycloak -> KeycloakAdminRestApi : Returns assignment result.
        KeycloakAdminRestApi -> StripeWebhookHandler: Confirms role assignment.
    </pre>
</div>
<p>💡 Since this is a continuation step, the actor is the same as in the <b>Stripe Checkout</b> use case.</p>

### Stripe diagrams {#stripe}
<div style="overflow:auto;">
<pre class="mermaid">
sequenceDiagram
    title: Stripe Checkout
    participant User
    participant Frontend
    participant WebVagasApi
    participant StripeApi
    participant StripeWebhookHandler
    participant Keycloak

    User->>Frontend: Logs into the frontend (without plan)
    Frontend->>User: Redirects to the plans page
    User->>Frontend: Selects the desired plan
    Frontend->>WebVagasApi: Calls /api/checkout with the selected plan
    WebVagasApi->>StripeApi: Creates checkout session
    StripeApi-->>WebVagasApi: Returns checkout_url
    WebVagasApi-->>Frontend: Returns checkout URL
    Frontend-->>User: Redirects to Stripe

    User->>StripeApi: Completes the payment
    StripeApi-->>User: Redirects to success_url or cancel_url

    StripeApi->>StripeWebhookHandler: Sends payment event
    StripeWebhookHandler->>Keycloak: See the role assignment step

    User->>Frontend: Returns to the app after payment
    Frontend->>Keycloak: Optionally requests a new token
    Keycloak-->>Frontend: Issues new token with the role representing the plan
    Frontend-->>User: Grants access according to the plan role
</pre>
</div>

<div style="overflow: auto;"> 
    <pre class="mermaid"> 
    zenuml title Stripe Checkout
        @Actor User
        Frontend
        WebVagasApi
        StripeApi
        StripeWebhookHandler
        Keycloak

        User -> Frontend : Logs into the frontend (without plan).
        Frontend -> User : Redirects to the plans page.
        User -> Frontend : Selects the desired plan.
        Frontend -> WebVagasApi: Calls /api/checkout with the selected plan.
        WebVagasApi -> StripeApi : Creates checkout session.
        StripeApi -> WebVagasApi : Returns checkout_url.
        WebVagasApi -> Frontend : Returns checkout URL.
        Frontend -> User : Redirects to Stripe.

        User -> StripeApi : Completes the payment.
        StripeApi -> User : Redirects to success_url or cancel_url.

        StripeApi -> StripeWebhookHandler : Sends payment event.

        StripeWebhookHandler -> Keycloak : See the role assignment step.

        User -> Frontend : Returns to the app after payment.
        Frontend -> Keycloak : Optionally requests a new token.
        Keycloak -> Frontend : Issues new token with the role representing the plan.
        Frontend -> User : Grants access according to the plan role.
    </pre>
</div>
<p>💡 The <b>Role Assignment in Keycloak</b> step happens right after the payment event is sent. See more details <a href="#kc-role-attribuition">here</a> 👈.</p>

### Web application diagrams {#web}
<div style="overflow: auto;"> 
    <pre class="mermaid"> 
    zenuml title User Preferences Configuration
        @Actor User
        Frontend
        WebVagasApi
        @Database WebVagasApiDB

        User -> Frontend : Accesses the User Preferences configuration screen.
        Frontend -> WebVagasApi : Sends REST call to fetch User Preferences.
        WebVagasApi -> Frontend : Returns User Preferences.
        User -> Frontend : Fills out and submits the Preferences form.
        Frontend -> WebVagasApi : Sends REST call to save User Preferences.
        WebVagasApi -> WebVagasApiDB : Saves User Preferences.
        WebVagasApiDB -> WebVagasApi : Returns update result.
        WebVagasApi -> Frontend : Returns REST response.
        Frontend -> User : Displays confirmation message that preferences were saved.
    </pre>
</div>

<p>💡 If the user doesn’t have saved preferences yet, the form is loaded with default preferences.</p>

### Admin API diagrams {#admin-api}
<div style="overflow:auto;">
<pre class="mermaid">
sequenceDiagram
    title Sends scheduled messages
    participant Worker
    participant KeycloakAdminApi
    participant WebVagasApi
    participant WebVagasApiDB
    participant JSearchApi
    participant ShortUrlApi
    participant TwillioApi

    Worker->>KeycloakAdminApi: Sends REST call to retrieve users filtered by plan
    KeycloakAdminApi-->>Worker: Returns users by plan

    Worker->>WebVagasApi: Sends REST call to retrieve User Preferences
    WebVagasApi->>WebVagasApiDB: Queries User Preferences
    WebVagasApiDB-->>WebVagasApi: Returns preferences
    WebVagasApi-->>Worker: Returns User Preferences

    Worker->>JSearchApi: Sends REST call to fetch job listings
    JSearchApi-->>Worker: Returns filtered job results

    Worker->>WebVagasApi: Checks if users already received those job listings
    WebVagasApi-->>Worker: Returns DB projection with IDs and boolean flags

    Worker->>ShortUrlApi: Sends request with list of URLs to shorten
    ShortUrlApi-->>Worker: Returns shortened URLs for message creation

    Worker->>TwillioApi: Builds message and sends it through Twilio API
</pre>
</div>
<div style="overflow: auto;"> 
    <pre class="mermaid"> 
    zenuml title Sends scheduled
        @Actor Worker
        KeycloakAdminApi
        WebVagasApi
        ShortUrlApi
        @Database WebVagasApiDB
        JSearchApi
        TwillioApi

        Worker -> KeycloakAdminApi : Sends REST call to retrieve users filtered by plan.
        KeycloakAdminApi -> Worker : Returns users by plan.
        Worker -> WebVagasApi : Sends REST call to retrieve User Preferences.
        WebVagasApi -> WebVagasApiDB : Queries User Preferences.
        WebVagasApiDB -> WebVagasApi : Returns preferences.
        WebVagasApi -> Worker : Returns User Preferences.
        Worker -> JSearchApi : Sends REST call to fetch job listings.
        JSearchApi -> Worker : Returns filtered job results.
        Worker -> WebVagasApi : Checks if users already received those job listings.
        WebVagasApi -> Worker : Returns DB projection with IDs and boolean flags indicating whether each record already exists.
        Worker -> ShortUrlApi : Sends request with list of URLs to shorten.
        ShortUrlApi -> Worker : Returns shortened URLs for message creation.
        Worker -> TwillioApi : Builds message and sends it through Twilio API.
    </pre>
</div>
<p>💡 The process performed by the <b>@Worker</b> is scheduled to run at fixed times.</p> 
<p>💡 Job filters are based on User Preferences.</p>