create table workspace_invites (
    id bigserial primary key,
    workspace_id bigint not null references workspaces(id) on delete cascade,
    token varchar(100) not null unique,
    role varchar(20) not null,
    created_by bigint not null references users(id),
    created_at timestamp not null default now(),
    expires_at timestamp,
    used boolean not null default false
);