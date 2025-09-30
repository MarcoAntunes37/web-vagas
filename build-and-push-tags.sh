#!/bin/bash

set -e

DOCKERHUB_USER="$1"
REPO_NAME="Webvagas"

if [ -z "$DOCKERHUB_USER" ]; then
  echo "Uso: ./build_and_push.sh <dockerhub-usuario>"
  exit 1
fi

ROOT_DIR=$(pwd)

for dir in */ ; do
  dir=${dir%/}
  echo -e "\n🚀 Processando projeto: $dir"
  cd "$ROOT_DIR/$dir"

  # Detectar tipo do projeto
  if [[ -f "pom.xml" ]]; then
    echo "🔧 Projeto Maven detectado, buildando..."
    ./mvnw clean package -DskipTests || mvn clean package -DskipTests
  elif [[ -f "angular.json" ]]; then
    echo "🌐 Projeto Angular detectado, buildando..."
    npm install
    npm run build
  else
    echo "⚠️  Tipo de projeto não reconhecido, pulando $dir."
    continue
  fi

  # Criar imagem Docker com tag específica
  IMAGE_NAME="${DOCKERHUB_USER}/${REPO_NAME}:${dir}"
  echo "🐳 Buildando imagem Docker: $IMAGE_NAME"
  docker build -t "$IMAGE_NAME" .

  # Enviar para Docker Hub
  echo "📤 Enviando imagem para Docker Hub..."
  docker push "$IMAGE_NAME"
done

echo -e "\n✅ Todos os projetos foram buildados e enviados com sucesso para ${DOCKERHUB_USER}/${REPO_NAME}!"