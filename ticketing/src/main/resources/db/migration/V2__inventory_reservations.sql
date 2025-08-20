create table if not exists seats (
  id bigserial primary key,
  venue_id bigint not null references venues(id) on delete cascade,
  section_label varchar(64),
  row_label varchar(64),
  seat_number varchar(64),
  type varchar(32),
  meta text
);
create index if not exists idx_seats_venue on seats(venue_id);

create table if not exists seat_inventory (
  id bigserial primary key,
  event_id bigint not null references events(id) on delete cascade,
  seat_id bigint not null references seats(id) on delete cascade,
  status varchar(16) not null default 'AVAILABLE',
  lock_expires_at timestamptz,
  version bigint,
  constraint uk_event_seat unique(event_id, seat_id)
);
create index if not exists idx_inventory_event on seat_inventory(event_id);
create index if not exists idx_inventory_status on seat_inventory(status);

create table if not exists reservations (
  id bigserial primary key,
  user_id bigint not null references users(id),
  event_id bigint not null references events(id) on delete cascade,
  items_json jsonb not null,
  status varchar(16) not null default 'PENDING',
  reserved_until timestamptz not null,
  idempotency_key varchar(255) unique,
  created_at timestamptz not null default now()
);
create index if not exists idx_reservations_event on reservations(event_id);
