FROM openjdk:17 AS builder

# 빌드에 필요한 소스 코드 및 의존성 복사
WORKDIR /workspace
COPY . .

# Gradle을 사용하여 애플리케이션 빌드
RUN ./gradlew build

# 빌드된 .jar 파일을 복사하여 사용할 준비
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 실행환경 설정
FROM openjdk:17
COPY --from=builder /workspace/app.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
