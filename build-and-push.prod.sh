#!/bin/bash

set -e

DOCKERHUB_USER="$1"

if [ -z "$DOCKERHUB_USER" ]; then
  echo "Uso: ./build_and_push.prod.sh <dockerhub-usuario>"
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

  # Criar imagem Docker
  IMAGE_NAME="${DOCKERHUB_USER}/${dir}:latest"
  echo "🐳 Buildando imagem Docker: $IMAGE_NAME"
  docker build -t "$IMAGE_NAME" .

  # Enviar para Docker Hub
  echo "📤 Enviando imagem para Docker Hub..."
  docker push "$IMAGE_NAME"
done

echo -e "\n✅ Todos os projetos foram processados com sucesso!"