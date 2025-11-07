#!/bin/bash
set -e

ROOT_DIR=$(pwd)
BASE_DIR="$ROOT_DIR/webvagas"

DOCKERHUB_USER="$1"

if [ -z "$DOCKERHUB_USER" ]; then
  echo "Uso: ./build_and_push.prod.sh <dockerhub-usuario>"
  exit 1
fi

echo "🏗️  Iniciando build dos subprojetos dentro de: $BASE_DIR"

if [[ ! -d "$BASE_DIR" ]]; then
  echo "❌ Diretório 'webvagas' não encontrado. Abortando."
  exit 1
fi

# Percorre os subdiretórios dentro de webvagas, exceto o próprio webvagas/pai
for dir in "$BASE_DIR"/*/ ; do
  dir=${dir%/}
  project=$(basename "$dir")

  # ⚠️ IGNORA o projeto pai caso detectado
  if [[ "$project" == "webvagas" ]]; then
    echo -e "\n⏭️  Ignorando projeto pai: $project"
    continue
  fi

  echo -e "\n🚀 Processando projeto: $project"
  cd "$dir"

  # Detectar tipo do projeto
  if [[ -f "pom.xml" ]]; then
    echo "| 🔧 Projeto Maven detectado, buildando..."
    # Garante permissão de execução ao mvnw, se existir
    if [[ -f "./mvnw" ]]; then
      chmod +x ./mvnw
      ./mvnw clean package -DskipTests
    else
      echo "| ⚙️ mvnw não encontrado, tentando Maven global..."
      mvn clean package -DskipTests
    fi
  elif [[ -f "angular.json" ]]; then
    echo "| 🌐 Projeto Angular detectado, buildando..."
    npm install
    npm run build
  else
    echo "| ⚠️ Tipo de projeto não reconhecido, pulando $project."
    cd "$ROOT_DIR"
    continue
  fi

  # Criar imagem Docker
  IMAGE_NAME="${DOCKERHUB_USER}/${project}:latest"
  echo "| 🐳 Buildando imagem Docker: $IMAGE_NAME"
  docker build -t "$IMAGE_NAME" .

  # Enviar para Docker Hub
  echo "| 📤 Enviando imagem para Docker Hub..."
  docker push "$IMAGE_NAME"

  cd "$ROOT_DIR"
done

echo -e "\n✅ Todos os builds concluídos com sucesso."