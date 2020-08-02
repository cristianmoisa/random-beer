FROM adoptopenjdk:11-jre-hotspot
ENV PORT 8080
ENV CLASSPATH /opt/lib
EXPOSE 8080

COPY pom.xml beer-backend/target/lib* /opt/lib/
COPY target/*.jar /opt/app.jar
WORKDIR /opt
CMD ["java", "-jar", "app.jar"]