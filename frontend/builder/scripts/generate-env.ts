import * as fs from 'fs';
import * as path from 'path';
import * as dotenv from 'dotenv';

const envFile = path.resolve(process.cwd(), '.env.prod');

dotenv.config({ path: envFile });

const envDir = path.resolve('src/app/environment');
const envFileTs = path.join(envDir, 'environment.ts');

const environment = {
    production: true,
    userPreferencesApiUrl: process.env['NG_APP_USER_PREFERENCES_API_URL'],
    checkoutSessionApiUrl: process.env['NG_APP_CHECKOUT_SESSION_API_URL'],
    kcConfigRealm: process.env['NG_APP_KC_CONFIG_REALM'],
    kcConfigUrl: process.env['NG_APP_KC_CONFIG_URL'],
    kcConfigClientId: process.env['NG_APP_KC_CONFIG_CLIENT_ID'],
};

fs.mkdirSync(envDir, { recursive: true });

fs.writeFileSync(
    envFileTs,
    `export const environment = ${JSON.stringify(environment, null, 2)};\n`
);

console.log(`✔️  Gerado: ${envFileTs}`);