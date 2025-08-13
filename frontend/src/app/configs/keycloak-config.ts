import {
    provideKeycloak,
    createInterceptorCondition,
    IncludeBearerTokenCondition,
    INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
    UserActivityService,
    withAutoRefreshToken,
    AutoRefreshTokenService
} from 'keycloak-angular';
import { environment } from '../environment/environment';

const { kcConfigRealm: realm, kcConfigUrl: url, kcConfigClientId: clientId } = environment

function escapeRegExp(string: string) {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

const regexString = `^(${escapeRegExp(url)})(\\/.*)?$`;

const localhostCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
    urlPattern: new RegExp(regexString, 'i'),
});

export const provideKeycloakAngular = () =>
    provideKeycloak({
        config: {
            realm: realm,
            url: url,
            clientId: clientId
        },
        initOptions: {
            onLoad: 'login-required',
            silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html',
            checkLoginIframe: false,
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