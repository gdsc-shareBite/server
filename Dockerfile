FROM adoptopenjdk/openjdk1

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew build --exclude-task test

RUN cp ./build/libs/*.jar ./app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod" ,"app.jar"]

# trigger build test1