FROM maven as maven_builder
ENV HOME=/app
WORKDIR ${HOME}
COPY ./pom.xml ${HOME}/pom.xml
COPY ./src ./src
RUN mvn clean install
ADD . ${HOME}

FROM tomcat:8.5.76-jdk8
COPY --from=maven_builder /app/target/wikipedia-inator-1.0.war /usr/local/tomcat/webapps