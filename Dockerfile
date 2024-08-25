FROM amd64/amazoncorretto:21
WORKDIR /app
VOLUME ["/log"]
COPY ./clody-app/build/libs/clody-app-21.jar /app/clody-app.jar
ENTRYPOINT ["java", "-jar", "clody-app.jar", "--spring.profiles.active=dev"]
