#!/usr/bin/env bash
set -euo pipefail

# Usage: ./deploy.sh <image> <tag> <container_name> <port>
IMAGE="${1:-}"
TAG="${2:-latest}"
CONTAINER_NAME="${3:-chatbot-app}"
PORT="${4:-8080}"

if [ -z "$IMAGE" ]; then
  echo "Image name is required" >&2
  exit 1
fi

echo "[Deploy] Pulling image: ${IMAGE}:${TAG}"
docker pull "${IMAGE}:${TAG}"

echo "[Deploy] Stopping existing container if running: ${CONTAINER_NAME}"
if docker ps -q -f name="^/${CONTAINER_NAME}$" >/dev/null; then
  docker stop "${CONTAINER_NAME}" || true
fi

echo "[Deploy] Removing existing container if present: ${CONTAINER_NAME}"
if docker ps -aq -f name="^/${CONTAINER_NAME}$" >/dev/null; then
  docker rm "${CONTAINER_NAME}" || true
fi

echo "[Deploy] Starting container"
docker run -d \
  --name "${CONTAINER_NAME}" \
  -p "${PORT}:8080" \
  -e CHATBOT_API_KEY="${CHATBOT_API_KEY:-}" \
  --restart unless-stopped \
  "${IMAGE}:${TAG}"

echo "[Deploy] Done. Container status:"
docker ps -f name="^/${CONTAINER_NAME}$"


