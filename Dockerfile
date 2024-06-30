FROM amd64/amazoncorretto:21
WORKDIR /app
VOLUME ["/log"]
COPY ./build/libs/server-0.0.1-SNAPSHOT.jar /app/clody.jar
CMD ["java","-Dspring.profiles.actove=dev", "-jar", "clody.jar"]
