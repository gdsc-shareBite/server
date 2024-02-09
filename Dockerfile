FROM azul/zulu-openjdk:17

RUN apt-get update && \
    apt-get install -y gradle

WORKDIR /app

COPY build.gradle .
COPY gradle ./gradle
RUN ./gradlew dependencies

COPY src ./src

RUN ./gradlew build

RUN cp ./build/libs/*.jar /app/app.jar

EXPOSE 8081

CMD ["java", "-jar", "/app/app.jar", "-Dspring.profiles.active=prod"]
