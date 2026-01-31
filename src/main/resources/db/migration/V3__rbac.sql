-- 1) Roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,        -- OWNER / ADMIN / MEMBER / VIEWER
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

-- 2) Permissions
CREATE TABLE IF NOT EXISTS permissions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,       -- e.g. WORKSPACE_CREATE
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

-- 3) Role-Permissions mapping (many-to-many)
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    permission_id BIGINT NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

-- -----------------------------
-- Seed built-in roles
-- -----------------------------
INSERT INTO roles (code, name, description) VALUES
('OWNER',  'Owner',  'Full access to workspace'),
('ADMIN',  'Admin',  'Manage workspace settings and members'),
('MEMBER', 'Member', 'Standard member access'),
('VIEWER', 'Viewer', 'Read-only access')
ON CONFLICT (code) DO NOTHING;

-- -----------------------------
-- Seed permissions (initial set)
-- Workspace basics
-- -----------------------------
INSERT INTO permissions (code, name, description) VALUES
('WORKSPACE_CREATE', 'Create workspace', 'Create a new workspace'),
('WORKSPACE_READ',   'Read workspace',   'View workspace info'),
('WORKSPACE_UPDATE', 'Update workspace', 'Update workspace settings'),
('WORKSPACE_DELETE', 'Delete workspace', 'Delete workspace')
ON CONFLICT (code) DO NOTHING;

-- Member basics
INSERT INTO permissions (code, name, description) VALUES
('MEMBER_READ',      'Read members',      'View member list'),
('MEMBER_INVITE',    'Invite members',    'Invite a user to workspace'),
('MEMBER_REMOVE',    'Remove members',    'Remove a user from workspace'),
('MEMBER_ROLE_SET',  'Set member role',   'Change member role')
ON CONFLICT (code) DO NOTHING;

-- Project basics
INSERT INTO permissions (code, name, description) VALUES
('PROJECT_CREATE', 'Create project', 'Create a project in workspace'),
('PROJECT_READ',   'Read project',   'View projects'),
('PROJECT_UPDATE', 'Update project', 'Update project'),
('PROJECT_DELETE', 'Delete project', 'Delete project')
ON CONFLICT (code) DO NOTHING;

-- -----------------------------
-- Map roles -> permissions
-- -----------------------------

-- Helper: OWNER gets all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON 1=1
WHERE r.code = 'OWNER'
ON CONFLICT DO NOTHING;

-- ADMIN: everything except destructive delete workspace
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'WORKSPACE_READ','WORKSPACE_UPDATE',
    'MEMBER_READ','MEMBER_INVITE','MEMBER_REMOVE','MEMBER_ROLE_SET',
    'PROJECT_CREATE','PROJECT_READ','PROJECT_UPDATE','PROJECT_DELETE'
)
WHERE r.code = 'ADMIN'
ON CONFLICT DO NOTHING;

-- MEMBER: normal usage (no member management, no workspace settings)
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'WORKSPACE_READ',
    'MEMBER_READ',
    'PROJECT_CREATE','PROJECT_READ','PROJECT_UPDATE'
)
WHERE r.code = 'MEMBER'
ON CONFLICT DO NOTHING;

-- VIEWER: read-only
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'WORKSPACE_READ',
    'MEMBER_READ',
    'PROJECT_READ'
)
WHERE r.code = 'VIEWER'
ON CONFLICT DO NOTHING;