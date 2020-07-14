FROM tomcat:7-jdk8-adoptopenjdk-hotspot
ARG WAR_FILENAME=library.course-0.0.1-SNAPSHOT.war
ARG TOMCAT_PATH=/usr/local/tomcat
ADD target/$WAR_FILENAME $TOMCAT_PATH/webapps/

# настройки контекного пути приложения
ADD docker/server.xml $TOMCAT_PATH/conf/server.xml