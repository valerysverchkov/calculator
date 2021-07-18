FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAVA_OPTS="-Xms512m -Xmx1g"
ARG JAR_IMAGE=calculator.jar
ARG JAR_FILE=build/libs/calculator-0.1.jar
WORKDIR /opt/app
COPY ${JAR_FILE} ${JAR_IMAGE}
ENTRYPOINT ["java", "-jar", "calculator.jar"]

