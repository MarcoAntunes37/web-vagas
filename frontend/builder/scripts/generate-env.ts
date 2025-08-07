import * as fs from 'fs';
import * as path from 'path';
import * as dotenv from 'dotenv';

dotenv.config();
const envDir = path.resolve('src/app/environment');
const envFile = path.join(envDir, 'environment.ts');
const environment = {
    production: false,
    userPreferencesApiUrl: process.env['NG_APP_USER_PREFERENCES_API_URL'],
    userPreferencesJsearchApiUrl: process.env['NG_APP_USER_PREFERENCES_JSEARCH_API_URL'],
    productsApiUrl: process.env['NG_APP_PRODUCTS_API_URL'],
    checkoutSessionApiUrl: process.env['NG_APP_CHECKOUT_SESSION_API_URL']
};

fs.mkdirSync(envDir, { recursive: true });

fs.writeFileSync(
    envFile,
    `export const environment = ${JSON.stringify(environment, null, 2)};\n`
);

console.log(`✔️  Gerado: ${envFile}`);