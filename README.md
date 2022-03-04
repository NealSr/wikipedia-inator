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

## Sample Calls and responses:
```
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=Coffee'
Your search for: Coffee contained 40 matches.
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=Test'
Your search for: Test contained 7 matches.
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=test'
Your search for: test contained 7 matches.
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=Neal'
Your search for: Neal contained 228 matches.
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=Cincinnati'
Your search for: Cincinnati contained 173 matches.
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=neal'
Your search for: neal contained 0 matches.
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=Java'
Your search for: Java contained 88 matches.
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=java'
Your search for: java contained 2 matches.
$ curl 'http://localhost:8080/wikipedia-inator-1.0/hello?title=Test&raw=true'
Your search for: Test contained 7 matches.

RAW DATA:
{"parse":{"title":"Test","pageid":11089416,"text":{"*":"<div class=\"mw-parser-output\"><table role=\"presentation\" class=\"mbox-small plainlinks sistersitebox\" style=\"background-color:#f9f9f9;border:1px solid #aaa;color:#000\">\n<tbody><tr>\n<td class=\"mbox-image\"><img alt=\"\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/9/99/Wiktionary-logo-en-v2.svg/40px-Wiktionary-logo-en-v2.svg.png\" decoding=\"async\" width=\"40\" height=\"40\" class=\"noviewer\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/9/99/Wiktionary-logo-en-v2.svg/60px-Wiktionary-logo-en-v2.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/9/99/Wiktionary-logo-en-v2.svg/80px-Wiktionary-logo-en-v2.svg.png 2x\" data-file-width=\"512\" data-file-height=\"512\" /></td>\n<td class=\"mbox-text plainlist\">Look up <i><b><a href=\"https://en.wiktionary.org/wiki/test\" class=\"extiw\" title=\"wiktionary:test\">test</a></b></i>, <i><b><a href=\"https://en.wiktionary.org/wiki/testing\" class=\"extiw\" title=\"wiktionary:testing\">testing</a></b></i>, <i><b><a href=\"https://en.wiktionary.org/wiki/Test\" class=\"extiw\" title=\"wiktionary:Test\">Test</a></b></i>, or <i><b><a href=\"https://en.wiktionary.org/wiki/TEST\" class=\"extiw\" title=\"wiktionary:TEST\">TEST</a></b></i> in Wiktionary, the free dictionary.</td></tr>\n</tbody></table>\n<p><b>Test(s)</b>, <b>testing</b>, or <b>TEST</b> may refer to:\n</p>\n<ul><li><a href=\"/wiki/Test_(assessment)\" title=\"Test (assessment)\">Test (assessment)</a>, an educational assessment intended to measure the respondents' knowledge or other abilities</li></ul>\n<style data-mw-deduplicate=\"TemplateStyles:r1044870489\">@media all and (max-width:720px){body.skin-minerva .mw-parser-output .tocright{display:none}.mw-parser-output .tocright{width:100%!important}}@media all and (min-width:720px){.mw-parser-output .tocright{float:right;clear:right;width:auto;margin:0 0 0.5em 1em}.mw-parser-output .tocright-clear-left{clear:left}.mw-parser-output .tocright-clear-both{clear:both}.mw-parser-output .tocright-clear-none{clear:none}}</style><div class=\"tocright\"></div>\n<!-- \nNewPP limit report\nParsed by mw1357\nCached time: 20220304092706\nCache expiry: 1814400\nReduced expiry: false\nComplications: []\nCPU time usage: 0.048 seconds\nReal time usage: 0.059 seconds\nPreprocessor visited node count: 132/1000000\nPost\u2010expand include size: 2275/2097152 bytes\nTemplate argument size: 266/2097152 bytes\nHighest expansion depth: 9/100\nExpensive parser function count: 0/500\nUnstrip recursion depth: 0/20\nUnstrip post\u2010expand size: 516/5000000 bytes\nLua time usage: 0.005/10.000 seconds\nLua memory usage: 552883/52428800 bytes\nNumber of Wikibase entities loaded: 0/400\n-->\n<!--\nTransclusion expansion time report (%,ms,calls,template)\n100.00%   47.945      1 -total\n 67.36%   32.298      1 Template:Wiktionary\n 60.34%   28.930      1 Template:Sister_project\n 48.14%   23.080      1 Template:Side_box\n 32.31%   15.490      1 Template:TOC_right\n-->\n</div>"}}}
```