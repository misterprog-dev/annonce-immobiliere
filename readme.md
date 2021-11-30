# Real estate management
App to manage real estate advertisements (creating, reading, updating and deleting).
The project contains back and front in same global folder.

## Database
- database: annonceimmobiliere
- user: annonceimmobiliere
- password: annonceimmobiliere

To install database
```bash
psql -Upostgres
CREATE database annonceimmobiliere template template0;
CREATE ROLE annonceimmobiliere WITH PASSWORD 'annonceimmobiliere' NOCREATEDB LOGIN VALID UNTIL 'infinity';
\c annonceimmobiliere
ALTER schema public owner to annonceimmobiliere;
```

## Backend
- Spring boot : 2.6.1
- Java 11
- Postgresql 12

## Frontend
- Angular 12
- Node 12.11.1

## To launch project

### Backend
1. Tomcat server

```bash
./gradlew bootrun
```

2. Launch TU
```bash
./gradlew clean build
```

### Frontend

1. To install all dependencies

```bash
cd frontend/
npm install
```

2. Start server

```bash
cd frontend
npm start
```
