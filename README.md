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

## Day 5
- Workspace context via X-Workspace-Id header
- ThreadLocal-based WorkspaceContext
- Request interceptor for workspace injection
- Workspace membership validation
- Tenant-level access isolation (403 on invalid workspace)

## Day 6
- RBAC data model (roles, permissions, role-permissions)
- Built-in system roles (OWNER / ADMIN / MEMBER / VIEWER)
- Permission codes for workspace, member, and project actions
- Seeded role-permission mappings via Flyway migration

## Day 7
- PermissionService to resolve permissions by user and workspace
- Role-to-permission resolution via RBAC tables
- Database-based permission lookup (no cache yet)
- Cache interface reserved for future optimization
- Debug endpoint to retrieve current user permissions

## Day 8
- Custom permission annotation (@RequirePermission)
- AOP-based permission enforcement
- Workspace-aware authorization via WorkspaceContext
- Integration with PermissionService for RBAC checks
- Proper 403 responses for unauthorized access

