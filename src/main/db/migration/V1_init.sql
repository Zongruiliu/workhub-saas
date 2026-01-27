create table users (
  id bigserial primary key,
  email varchar(255) not null unique,
  password_hash varchar(255) not null,
  status varchar(30) not null default 'ACTIVE',
  created_at timestamptz not null default now()
);