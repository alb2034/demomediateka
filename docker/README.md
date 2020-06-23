## Развертывание приложения на Tomcat 7 помощи docker-образа 

### При необходимости запуска приложения в режиме отладки:

в файле docker/_Dockerfile_ расскомментировать и закомментировать строки:
   ```bash
#CMD ["catalina.sh", "jpda", "run"]
CMD ["catalina.sh", "run"]
   ```
в соответствующем порядке.

### Сборка:

   ```bash
docker build --rm -t learn-mediateka .
   ```
### Запуск:

   ```bash
docker run --name mediateka
-v ${ABSOLUTE_PROJECT_PATH}/target/${WAR_FILENAME}:/usr/local/tomcat/webapps/${WAR_FILENAME}
-p 8080:8080 -p 8000:8000 learn-mediateka
   ```

### При необходимости подключения к серверу для отладки:

- IDE Run/Debug Configurations
- Create Remote Configuration
- Host: **localhost**, port: **8000**
