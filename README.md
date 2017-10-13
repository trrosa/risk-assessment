# risk-assessment
Simple Demo of SpringBoot and AngularJS

# Bakend
## Build Production Profile with PostgreSQL
- url: jdbc:postgresql://localhost:5432/postgres 
- user: postgres 
- pass: postgres
- build: mvn clean install -Pprod

## Build Dev Profile with H2 Memory
- build: mvn clean install -Pdev

## RUN App back
- mvn spring-boot:run or java -jar target/riskassessment-0.0.1-SNAPSHOT.jar

# Frontend
## Build Production uglified and minified
- npm install
- bower install
- gulp build
- The sources was generated in folder target/www/

## Run App front
- gulp serve
