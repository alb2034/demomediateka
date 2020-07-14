## Развертывание приложения на Tomcat 7 в docker-контейнере 

### Первичная сборка и запуск:
   ```bash
docker-compose up app
   ```
### Последующие запуск и остановка:

   ```bash
docker-compose start app
docker-compose stop app
   ```
### Доступ к приложению:
   ```bash
http://localhost:8080/
   ```

### Отладка:
a. запустить контейнер в режиме отладки:
   ```bash
docker-compose up app_debug
   ```

b. подключиться к контейнеру:
- IDE Run/Debug Configurations
- Create Remote Configuration
- Host: **localhost**, port: **8000**

### Удаление контейнеров и образа:
   ```bash
docker-compose down --rmi all
   ```