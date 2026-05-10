# ReclamApp

## Description du projet

ReclamApp est une application web de gestion des réclamations clients développée avec Spring Boot et Angular.

L’objectif du projet est de digitaliser le processus de gestion des réclamations afin de faciliter le suivi des demandes clients et améliorer la communication entre les clients et les agents SAV.

L’application permet :
- aux clients de créer et consulter leurs réclamations
- aux agents SAV de gérer les réclamations
- d’ajouter des suivis
- de modifier le statut des réclamations
- de générer des rapports

Ce projet a été réalisé dans le cadre d’un projet JEE Full Stack.

---

# Déploiement de l’application

## Frontend déployé

Application disponible sur :

```bash
https://reclamapp-frontend-47755772899.us-central1.run.app
```

---

# Fonctionnalités

## Gestion des clients
- Ajouter un client
- Modifier un client
- Supprimer un client
- Consulter la liste des clients

## Gestion des réclamations
- Ajouter une réclamation
- Modifier une réclamation
- Supprimer une réclamation
- Consulter les réclamations
- Affecter un agent SAV
- Modifier le statut d’une réclamation

## Gestion des agents SAV
- Ajouter un agent
- Modifier un agent
- Supprimer un agent
- Consulter la liste des agents

## Gestion des suivis
- Ajouter un suivi
- Consulter les suivis d’une réclamation

## Rapports
- Rapport des suivis
- Rapport de satisfaction

---

# Technologies utilisées

## Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- Lombok
- Jakarta Validation

## Frontend
- Angular
- TypeScript
- Bootstrap
- HTML
- CSS

## Base de données
- MySQL

## Outils
- Git
- GitHub
- Postman
- Eclipse
- Visual Studio Code

---

# Architecture du projet

```bash
ReclamApp/
│
├── ReclamAppBackend/
│
└── ReclamAppFrontend/
```

---

# Installation du projet

## 1. Cloner le repository

```bash
git clone https://github.com/OnsElfekih/ReclamApp.git
```

---

# Installation du Backend

## 2. Accéder au dossier backend

```bash
cd ReclamApp/ReclamAppBackend
```

---

## 3. Configurer MySQL

Créer une base de données nommée :

```sql
reclamapp
```

---

## 4. Configurer le fichier application.properties

Chemin :

```bash
src/main/resources/application.properties
```

Configuration :

```properties
spring.application.name=ReclamApp

server.port=9090

spring.datasource.url=jdbc:mysql://localhost:3306/reclamapp?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 5. Installer les dépendances Maven

```bash
mvn clean install
```

---

## 6. Lancer le backend

```bash
mvn spring-boot:run
```

Ou lancer directement :

```bash
ReclamAppBackendApplication.java
```

Backend disponible sur :

```bash
http://localhost:9090
```

---

# Installation du Frontend

## 7. Accéder au dossier frontend

```bash
cd ../ReclamAppFrontend
```

---

## 8. Installer les dépendances Angular

```bash
npm install
```

---

## 9. Lancer Angular

```bash
ng serve
```

Frontend disponible sur :

```bash
http://localhost:4200
```

---

# API Backend

## Exemple API

### Récupérer les clients

```http
GET http://localhost:9090/api/clients
```

### Ajouter une réclamation

```http
POST http://localhost:9090/api/reclamations
```

Exemple JSON :

```json
{
  "client": {
    "id": 1
  },
  "produit": "Laptop Dell",
  "description": "Écran défectueux",
  "statut": "OUVERTE"
}
```

---

# Dockerisation du Backend

## Dockerfile

Créer un fichier `Dockerfile` dans le backend :

```Dockerfile
FROM eclipse-temurin:17

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java","-jar","app.jar"]
```

---

## Construire le projet Maven

```bash
mvn clean package
```

---

## Construire l’image Docker

```bash
docker build -t reclamapp-backend .
```

---

## Lancer le conteneur Docker

```bash
docker run -p 9090:9090 reclamapp-backend
```

---

# Docker Compose 
## docker-compose.yml

```yaml
version: '3.8'

services:

  mysql:
    image: mysql:8
    container_name: mysql_reclamapp
    restart: always
    environment:
      MYSQL_DATABASE: reclamapp
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"

  backend:
    build: .
    container_name: reclamapp_backend
    restart: always
    ports:
      - "9090:9090"
    depends_on:
      - mysql
```

---

# Déploiement

## Frontend Angular
Déployé sur Google Cloud Run.

Lien :
```bash
https://reclamapp-frontend-47755772899.us-central1.run.app
```

## Backend Spring Boot
Compatible avec :
- Google Cloud Run
- Render
- Railway

---

## 👥 Author

**Ons ELFEKIH**  
Business Intelligence Engineering Student
