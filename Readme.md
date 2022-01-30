# Circular Chess backend
[![Java CI with Maven](https://github.com/ccd-course/chess_backend/actions/workflows/maven.yml/badge.svg)](https://github.com/ccd-course/chess_backend/actions/workflows/maven.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


The backend mainly consist of:
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
  (The first loading of the page takes a long time, because it needs to fetch dependencies and is not optimized due to `npm start`)
- Backend [http://localhost:8080/](http://localhost:8080/)
- JavaDocs for Backend [http://localhost:80/](http://localhost:80/)

## Auto deployment
The develop branches of [frontend](https://github.com/ccd-course/frontend_app) and [backend](https://github.com/ccd-course/chess_backend) are auto deployed on every commit:
- [circularchess.net](https://circularchess.net)
- [api.circularchess.net](https://api.circularchess.net)

## Documentation
- [Generated JavaDocs](https://ccd-course.github.io/chess_backend/index.html)
- [API Documentation](https://api.circularchess.net/docs.html)
- [Architecture diagram](https://plantuml-server.kkeisuke.dev/svg/TLF1Ri8m3BtdAwmUAy7k4q8mD3OEaoO6fuf3Q3sMBKsw96L24_y-ErsX7UnGjNf-UNxFSIS-I1VgKWjZ2uHaxgm9zwP8u5i0p6nPMOCcGF8WIuJruWpvH10Zmpr_WrO-e46tOKpRdJ8tNkrCNKLWYPyr-k0tGeWZFRcsTH4xD5rVqEtHSSzUqpkPVw0fcFUuMZt30B8PsNDMQtIUbPTOMQ-2TOU52UXUPOwUQMUSMIolgDBbP2t8X2ToeViA9KDi6E0ONRTUvyhXLdfi9jIrEwt36xrLBeEoXgkCJ7DIZ99h7rt7KwnNo21PgT7qv1QJsEueUZbXE1n3TXbYSmlAl6CUl3YdQSZNvjAbT-RJ7FWpWJxtz_8Q9jy4bMkFLElTT5C_PsvP_NO3ufpo4kNBgK0ZLqPSx58Zs2NxUkjpfxO8QJeQMQCFu50MupJzvpGjt-Ncih6Afiy9hbIZnLFgDkf_nDu8siGJMblwgNu0)
- [sequence diagram](https://i.ibb.co/bF2JWFq/seq-chess-drawio-1.png)
