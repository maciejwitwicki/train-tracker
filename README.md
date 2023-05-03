# train-tracker
angular + spring boot 3, oauth2 secured

# Running locally
Follow the steps in given order
## Start docker compose

*Prerequisite*: docker daemon installed and running

```shell
docker compoose up
```
This will start:
- Postgres DB (to be used by Keycloak)
- Keycloak server
## Start train-tracker backend

*Prerequisite*: java 19 jdk installed

```shell
gradlew bootRun
```

This will start API service for train-tracker available at http://localhost:8080

Swagger UI is available at http://localhost:8080/swagger-ui.html

## Start train-tracker UI

*Prerequisite*: node 18 installed

```shell
cd ui
ng serve
```

This will start UI application available at http://localhost:4200
