create table if not exists outbox_events (
  id bigserial primary key,
  aggregate_type varchar(64),
  aggregate_id varchar(64),
  event_type varchar(64),
  payload jsonb,
  created_at timestamptz not null default now(),
  processed_at timestamptz
);

create table if not exists audit_logs (
  id bigserial primary key,
  actor_id bigint not null references users(id),
  action varchar(128) not null,
  entity varchar(128) not null,
  entity_id varchar(128) not null,
  metadata jsonb,
  created_at timestamptz not null default now()
);

-- Optional idempotency table if you want:
-- create table idempotency (...);
