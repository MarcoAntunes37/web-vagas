import {
    provideKeycloak,
    createInterceptorCondition,
    IncludeBearerTokenCondition,
    INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
    UserActivityService,
    withAutoRefreshToken,
    AutoRefreshTokenService
} from 'keycloak-angular';

const localhostCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
    urlPattern: /^(http:\/\/localhost:8181)(\/.*)?$/i
});

export const provideKeycloakAngular = () =>
    provideKeycloak({
        config: {
            realm: 'FlashVagas',
            url: 'http://localhost:8181',
            clientId: 'angular'
        },
        initOptions: {
            onLoad: 'login-required',
            silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html',
            checkLoginIframe: true,
            redirectUri: window.location.href
        },
        features: [
            withAutoRefreshToken({
                onInactivityTimeout: 'login',
                sessionTimeout: 60000
            })
        ],
        providers: [
            AutoRefreshTokenService,
            UserActivityService,
            {
                provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
                useValue: [localhostCondition]
            }
        ]
    });