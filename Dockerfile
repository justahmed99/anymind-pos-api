FROM openjdk:17-oracle
WORKDIR /app
COPY . /app
EXPOSE 9090 8080
ENTRYPOINT ["java", "-jar", "build/libs/pos-0.0.1-SNAPSHOT.jar"]
