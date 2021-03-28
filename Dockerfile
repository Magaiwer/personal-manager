FROM adoptopenjdk/openjdk11:alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG PROFILE
ARG ADDITIONAL_OPTS
ARG JAR_FILE=target/personal-manager*.jar
ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}
WORKDIR /opt/api
COPY ${JAR_FILE} /opt/api/personal-manager-api.jar
CMD java ${ADDITIONAL_OPTS} -jar personal-manager-api.jar --spring.profiles.active=${PROFILE}