FROM adoptopenjdk/openjdk11:alpine as base
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG PROFILE
ARG ADDITIONAL_OPTS
ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /app-api

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

USER root
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src

#FROM base as test
#RUN ["./mvnw", "test"]
#FROM base as development
#CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=dev", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9000'"]
FROM base as build
RUN  ./mvnw package -Dmaven.test.skip=true

FROM openjdk:11-jre-slim as production
COPY --from=build /app-api/target/personal-manager*.jar /app-api/personal-manager-api.jar
CMD java ${ADDITIONAL_OPTS} -jar /app-api/personal-manager-api.jar --spring.profiles.active=${PROFILE}