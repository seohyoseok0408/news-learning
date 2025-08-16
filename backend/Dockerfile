# 1단계: Build
FROM gradle:7.6.2-jdk17 AS builder

WORKDIR /app

# 의존성 캐싱을 위해 먼저 복사
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle


# 개행문자 오류 해결 [unix와 window 시스템 차이]
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew
RUN ./gradlew dependencies

# 소스 코드 복사 후 빌드
COPY . .
RUN ./gradlew clean build -x test

FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
