# Chess backend
[![Java CI with Maven](https://github.com/ccd-course/chess_backend/actions/workflows/maven.yml/badge.svg)](https://github.com/ccd-course/chess_backend/actions/workflows/maven.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


This is the backend part of the Chess project consist mainly of:
- Controllers
- Models
- Services

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 4](https://maven.apache.org)

## Run locally
### Backend only
```
mvn spring-boot:run
```
### Complete stack
```
git clone git@github.com:ccd-course/frontend_app.git
cd frontend_app
docker-compose up
```
Local instances can be found here:
- Frontend [http://localhost:3000/](http://localhost:3000/)
- Backend [http://localhost:8080/](http://localhost:8080/)
- JavaDocs for Backend [http://localhost:80/](http://localhost:80/)

## Auto deployment
The develop branches of [frontend](https://github.com/ccd-course/frontend_app) and [backend](https://github.com/ccd-course/chess_backend) are auto deployed on every commit:
- [chess.valentinriess.com](https://chess.valentinriess.com)
- [backend.chess.valentinriess.com](https://backend.chess.valentinriess.com)

## Documentation
- [Generated JavaDocs](https://docs.backend.chess.valentinriess.com)
- [API Documentation](https://backend.chess.valentinriess.com/docs.html)
