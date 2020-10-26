FROM adoptopenjdk:11-jre-openj9
RUN mkdir /opt/app
COPY ./target/springboot-tgymeleaf-web-app.jar /opt/app/springboot-tgymeleaf-web-app.jar
CMD ["java", "-jar", "/opt/app/springboot-tgymeleaf-web-app.jar"]
EXPOSE[8080:8080]
