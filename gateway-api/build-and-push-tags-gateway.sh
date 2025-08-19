#!/bin/bash

set -e

DOCKERHUB_USER="$1"
REPO_NAME="flashvagas"
PROJECT_NAME="gateway-api"

if [ -z "$DOCKERHUB_USER" ]; then
  echo "Uso: ./build_${PROJECT_NAME}.sh <dockerhub-usuario>"
  exit 1
fi

ROOT_DIR=$(pwd)

echo -e "\n🚀 Buildando projeto: $PROJECT_NAME"
cd "$ROOT_DIR"

# Detectar tipo do projeto
if [[ -f "pom.xml" ]]; then
  echo "🔧 Projeto Maven detectado, buildando..."
  ./mvnw clean package -DskipTests || mvn clean package -DskipTests
elif [[ -f "angular.json" ]]; then
  echo "🌐 Projeto Angular detectado, buildando..."
  npm install
  npm run build
else
  echo "⚠️  Tipo de projeto não reconhecido, nada a fazer."
  exit 1
fi

# Criar imagem Docker com tag específica
IMAGE_NAME="${DOCKERHUB_USER}/${REPO_NAME}:${PROJECT_NAME}"
echo "🐳 Buildando imagem Docker: $IMAGE_NAME"
docker build -t "$IMAGE_NAME" .

# Enviar para Docker Hub
echo "📤 Enviando imagem para Docker Hub..."
docker push "$IMAGE_NAME"

echo -e "\n✅ Projeto $PROJECT_NAME enviado para ${DOCKERHUB_USER}/${REPO_NAME}:${PROJECT_NAME} em $(date +"%Y-%m-%d %H:%M:%S")!"
