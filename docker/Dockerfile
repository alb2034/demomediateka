FROM tomcat:7-jdk8-adoptopenjdk-hotspot
ARG WAR_FILENAME=library.course-0.0.1-SNAPSHOT.war
ADD target/$WAR_FILENAME /usr/local/tomcat/webapps/

# настройки контекного пути приложения
ADD docker/server.xml /usr/local/tomcat/conf/server.xml

ENV JPDA_ADDRESS=8000
ENV JPDA_TRANSPORT=dt_socket

# запуск приложения в режиме отладки
#CMD ["catalina.sh", "jpda", "run"]

# запуск приложения в обычном режиме
CMD ["catalina.sh", "run"]