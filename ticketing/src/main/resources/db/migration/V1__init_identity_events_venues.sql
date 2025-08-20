create table if not exists users (
  id bigserial primary key,
  email varchar(255) not null unique,
  password_hash varchar(255) not null,
  roles varchar(255) not null,
  status varchar(32) not null default 'ACTIVE',
  name varchar(255),
  created_at timestamptz not null default now()
);

create table if not exists venues (
  id bigserial primary key,
  name varchar(255) not null,
  address text,
  timezone varchar(64) not null,
  seat_map_json jsonb,
  created_at timestamptz not null default now()
);

create table if not exists events (
  id bigserial primary key,
  organizer_id bigint not null references users(id),
  venue_id bigint not null references venues(id),
  name varchar(255) not null,
  description text,
  start_at timestamptz not null,
  end_at timestamptz not null,
  status varchar(32) not null default 'DRAFT',
  category varchar(128),
  image_url text,
  created_at timestamptz not null default now()
);

create table if not exists ticket_types (
  id bigserial primary key,
  event_id bigint not null references events(id) on delete cascade,
  name varchar(255) not null,
  price_cents bigint not null,
  currency varchar(16) not null,
  quota int not null,
  sales_start timestamptz,
  sales_end timestamptz,
  min_per_order int default 1,
  max_per_order int default 10
);
create index if not exists idx_ticket_types_event on ticket_types(event_id);
