# ShoeShop — интернет-магазин обуви

**Стек:** Java 21, Spring Boot 3, Thymeleaf, Spring Data JPA, PostgreSQL + Flyway, Docker, nginx (HTTPS).

## Запуск

```bash
bash setup.sh                 # создаёт .env со случайным паролем БД и запускает всё
# VirtualBox с пробросом портов 8080->80 и 8443->443:
bash setup.sh localhost 8443
```

Открыть: https://localhost (или https://localhost:8443).
Браузер предупредит о самоподписанном сертификате — это нормально для теста: «Дополнительно → Продолжить».

Вручную: `cp .env.example .env`, поменять `POSTGRES_PASSWORD`, затем `docker compose up -d --build`.

## Архитектура

```
Браузер ──:443 (HTTPS)──> nginx ──> app (Spring Boot :8080) ──> PostgreSQL
          :80 -> редирект на https
```

- Пароли и настройки — в `.env` (не попадает в Git и в образ).
- Схему БД ведёт Flyway: `src/main/resources/db/migration/V*.sql`. Hibernate только проверяет (`ddl-auto: validate`).
  Изменили сущность — добавьте новую миграцию `V3__...sql`, не правя старые.
- Старая БД (создана до Flyway) подхватывается автоматически (`baseline-on-migrate`).
- `/actuator` снаружи закрыт (404), порт 8080 приложения и порт БД наружу не опубликованы.
- Cookie сессии: `Secure`, `HttpOnly`, `SameSite=Lax`.

## Свой домен и настоящий сертификат

1. Положите `fullchain.pem` и `privkey.pem` в папку `./certs` (сервис `certs` их не тронет).
2. В `.env` укажите `SERVER_NAME=shop.example.com`.
3. Раскомментируйте HSTS в `nginx/default.conf.template`.
4. `docker compose up -d`.

Получить бесплатный сертификат Let's Encrypt можно на ВМ с публичным IP: `certbot certonly --standalone -d shop.example.com`
(остановите nginx на время выпуска), затем скопируйте файлы из `/etc/letsencrypt/live/shop.example.com/` в `./certs`.

## Полезное

```bash
docker compose logs -f app
docker compose down        # остановка, данные БД сохраняются
docker compose down -v     # остановка и удаление БД
```
