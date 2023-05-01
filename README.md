# DoD
Electrifying the Swedish Roleplaying game: Drager &amp; Dæmoner

## Badges
We don't need no stinking badges: https://www.youtube.com/watch?v=VqomZQMZQCQ but here you go:

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mpeki_dod&metric=coverage)](https://sonarcloud.io/project/overview?id=mpeki_dod)
[![Known Vulnerabilities](https://snyk.io/test/github/mpeki/dod/badge.svg)](https://snyk.io/test/github/mpeki/dod/badge.svg)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)

# GitHub
[Issue Tracking](https://github.com/mpeki/dod/projects/1) and [Wiki](https://github.com/mpeki/dod/wiki).

# Build & Test
Gradle is building, verifying and publishing the project.

Building the project from the commandline: `gradle build` - this will also run tests. To run e.g. backend tests alone
execute: `gradle :backend:test`

Running test coverage locally: `gradle test jacocoTestCoverageVerification --info` and view the report [here](./backend/build/reports/jacoco/test/html/index.html). 

## IntelliJ
When running tests via IntelliJ remember that the `test` profile should be active, this is achieved by setting the 
environment variable: `SPRING_PROFILES_ACTIVE=test`  

## Docker
[Jib](https://github.com/GoogleContainerTools/jib) and the 
[Jib gradle plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin) is used to containerize 
the project app.

# Releases
See the [CHANGELOG](CHANGELOG.md).

## Semantic Release
Currently using the [semantic-release project](https://semantic-release.gitbook.io/semantic-release/) to manage versions, 
since the project is being build with Gradle, the https://www.npmjs.com/package/gradle-semantic-release-plugin has been 
applied.

## Liquibase
To reset the initial schema creation yaml (01-create-schema.yaml) run: `gradle liquibaseDiffChangeLog`

## References
* Project structure: https://github.com/KengoTODA/gradle-boilerplate and https://github.com/spotbugs/spotbugs-gradle-plugin
* Junit 5 Parameterized tests: https://www.baeldung.com/parameterized-tests-junit-5

## Project Dependencies
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Lombok](https://projectlombok.org/)
* [Gradle](https://gradle.org/)
* [Jib](https://github.com/GoogleContainerTools/jib)
* [Semantic Release](https://semantic-release.gitbook.io/semantic-release/)
  * [A Semantic Release plugin for Gradle](https://www.npmjs.com/package/gradle-semantic-release-plugin)

