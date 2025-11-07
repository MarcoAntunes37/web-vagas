import * as fs from 'fs';
import * as path from 'path';
import * as dotenv from 'dotenv';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Detecta o ambiente atual
const currentEnv = process.env['APP_ENV'] || process.env['NODE_ENV'] || 'dev';
const envFile = path.resolve(__dirname, `../../.env.${currentEnv}`);
const fallbackEnv = path.resolve(__dirname, '../../.env.example');

console.log('==============================================');
console.log('🌍 Current environment:', currentEnv);
console.log('🔍 Looking for env file at:', envFile);
console.log('📂 Script directory:', __dirname);
console.log('==============================================');

// Listagem de arquivos no diretório do env
const envFolder = path.dirname(envFile);
console.log('\n📁 Listando conteúdo da pasta onde o .env deveria estar:');
try {
  const files = fs.readdirSync(envFolder, { withFileTypes: true });
  for (const f of files) {
    console.log(` - ${f.isDirectory() ? '📁' : '📄'} ${f.name}`);
  }
} catch (err) {
  console.error('❌ Erro ao listar o diretório:', err);
}

// Verifica existência do arquivo .env
if (!fs.existsSync(envFile)) {
  console.warn(`\n⚠️  Arquivo de ambiente não encontrado: ${envFile}`);
  
  if (fs.existsSync(fallbackEnv)) {
    console.log(`📄 Usando arquivo de fallback: ${fallbackEnv}`);
    try {
      fs.copyFileSync(fallbackEnv, envFile);
      console.log(`✅ Copiado .env.example para ${envFile}`);
    } catch (copyErr) {
      console.error('❌ Erro ao copiar o arquivo de fallback:', copyErr);
      process.exit(1);
    }
  } else {
    console.error('❌ Nenhum arquivo de ambiente encontrado (.env.dev ou .env.example)');
    process.exit(1);
  }
}

// Exibe os primeiros caracteres do arquivo (sem expor segredos)
console.log('\n📄 Preview do conteúdo do arquivo .env (parcial):');
try {
  const envPreview = fs.readFileSync(envFile, 'utf-8')
    .split('\n')
    .filter(line => line.trim() && !line.startsWith('#'))
    .slice(0, 10);
  envPreview.forEach(line => console.log('  ', line));
} catch (err) {
  console.error('⚠️  Não foi possível ler o arquivo .env:', err);
}

// Carrega as variáveis
dotenv.config({ path: envFile, override: true });

// Gera o arquivo Angular environment.ts
const envDir = path.resolve('src/app/environment');
const envFileTs = path.join(envDir, 'environment.ts');

const environment = {
  production: currentEnv === 'prod',
  userPreferencesApiUrl: process.env['NG_APP_USER_PREFERENCES_API_URL'],
  checkoutSessionApiUrl: process.env['NG_APP_CHECKOUT_SESSION_API_URL'],
  customerPortalApiUrl: process.env['NG_APP_CUSTOMER_PORTAL_API_URL'],
  kcConfigRealm: process.env['NG_APP_KC_CONFIG_REALM'],
  kcConfigUrl: process.env['NG_APP_KC_CONFIG_URL'],
  kcConfigClientId: process.env['NG_APP_KC_CONFIG_CLIENT_ID'],
};

// Cria diretório e grava o arquivo environment.ts
try {
  fs.mkdirSync(envDir, { recursive: true });
  fs.writeFileSync(
    envFileTs,
    `export const environment = ${JSON.stringify(environment, null, 2)};\n`
  );
  console.log(`\n✔️  Arquivo gerado com sucesso: ${envFileTs}`);
  console.log(`✔️  Baseado em: ${envFile}`);
} catch (err) {
  console.error('❌ Erro ao gerar environment.ts:', err);
  process.exit(1);
}

console.log('==============================================');
console.log('✅ Script generate-env.ts finalizado com sucesso!');
console.log('==============================================');