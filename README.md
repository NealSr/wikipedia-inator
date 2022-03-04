# wikipedia-inator
A Tomcat/Docker/Maven Proof of Concept that parses JSON from Wikipedia.

## Requirements
* Docker Desktop
* JDK 8
* Maven 3
* Apache Tomcat 8

## Overview
This project is a simple tomcat webapp that counts the number of times a wikipedia article's title
occurs in the article itself.  It is intended to be a 'hello world' style webapp, showing some 
simple dependency management with Maven, running code in Docker using a Dockerfile, executing 
a simple HTTP GET request, and handling query parameters in an HttpServlet.

## Build and Deploy
This project is built with maven either from a terminal manually or as part of the Docker steps below.
```
mvn clean install
```
Once built, the war file will be located in `./target/wikipedia-inator-1.0.war`
Copying this war to a running tomcat webapp directory will restart the servlet and allow requests to come through on a local machine.

## Docker Build and Deploy
This project can also be run in a Docker container.  The Dockerfile starts with a maven_builder instance to build the war, then copies the war to a tomcat docker image and launches tomcat.
1. Start in the root directory and build the Docker image.
```
docker build --tag=wikipedia-inator .
```
2. After it successfully builds the image you can then map a local port to the docker port 8080 and launch.
```
docker run -p 8080:8080 wikipedia-inator
```
The above will map port 80 (default http port) to port 8080 on the docker image (tomcat default).
3. You should see logs with CATALINA starting up in tomcat, including the following 2 messages:
```
Deployment of web application archive [/usr/local/tomcat/webapps/wikipedia-inator-1.0.war] has finished in [536] ms
Server startup in 632 ms
```
Any logging to System.out will also show up in your terminal as you hit the service.

## Usage
* The webapp servlet can be reached by navigating to `http://localhost/wikipedia-inator-1.0/hello` (or using Postman or cURL)
* by default the servlet will check for the article "Cincinnati" and count the string matches, then return the result in plain text.
* to search for a different article title, add the `title` query param e.g. `http://localhost/wikipedia-inator-1.0/hello?title=Neal`
* NOTE: The title parameter is case-sensitive.  Test and test will match different strings in the app.
* If you'd like to view the raw format, just add the `raw` query param e.g. `http://localhost/wikipedia-inator-1.0/hello?title=Test&raw=true`
* NOTE: the `raw` query parameter just needs to be present, the value doesn't matter for the proof-of-concept.
* Don't forget to stop your docker container when done. (Ctrl+C from the terminal is one way to stop it).