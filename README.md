# WorkHub SaaS

Multi-tenant SaaS system built with Java & Spring Boot.

## Day 1
- Spring Boot project setup
- PostgreSQL with Docker Compose
- Flyway database migration
- Health check endpoint (/ping)

## Day 2
- User entity & repository
- User registration API
- Request validation
- BCrypt password encryption
- Security permitAll for auth endpoints

## Day 3
- User login with AuthenticationManager
- JWT issuance using NimbusJwtEncoder (HS256)
- Spring Security Resource Server (JWT)
- Secured /me endpoint
- Explicit authentication error mapping

## Day 4
- Workspace and membership data model
- Flyway V2 migration (workspaces, workspace_members)
- Create workspace API with transactional member creation
- Owner role assigned automatically on creation
- List workspaces by authenticated user

