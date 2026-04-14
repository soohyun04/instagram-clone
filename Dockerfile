# 1. 빌드용 이미지
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

# 2. 실행용 이미지 (가볍게)
FROM openjdk:21-jdk-slim
WORKDIR /app

# build 결과 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 설정
EXPOSE 8080

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
