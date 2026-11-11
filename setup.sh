#!/usr/bin/env bash
# Создаёт .env со случайным паролем БД и запускает магазин.
# Использование: bash setup.sh [имя_сервера] [https_порт_снаружи]
#   bash setup.sh                 -> localhost, 443
#   bash setup.sh localhost 8443  -> для VirtualBox с пробросом 8443 -> 443
set -eu
cd "$(dirname "$0")"

SERVER_NAME="${1:-localhost}"
HTTPS_PORT="${2:-443}"

if [ -f .env ]; then
  echo ".env уже существует — оставляю как есть."
else
  PASS="$(tr -dc 'A-Za-z0-9' < /dev/urandom | head -c 24 || true)"
  cat > .env <<EOT
POSTGRES_DB=shop
POSTGRES_USER=shop
POSTGRES_PASSWORD=${PASS}
SERVER_NAME=${SERVER_NAME}
HTTPS_PUBLIC_PORT=${HTTPS_PORT}
EOT
  chmod 600 .env
  echo ".env создан (пароль БД сгенерирован)."
fi

docker compose up -d --build
echo
echo "Готово. Откройте: https://${SERVER_NAME}$( [ "$HTTPS_PORT" = "443" ] || printf ':%s' "$HTTPS_PORT" )"
