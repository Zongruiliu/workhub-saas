create table if not exists projects (
  id bigserial primary key,
  workspace_id bigint not null,
  name varchar(120) not null,
  description varchar(500),
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),

  constraint fk_projects_workspace
    foreign key (workspace_id) references workspaces(id) on delete cascade
);

create index if not exists idx_projects_workspace_id on projects(workspace_id);
create unique index if not exists uq_projects_workspace_name on projects(workspace_id, name);