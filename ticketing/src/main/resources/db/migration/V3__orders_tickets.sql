create table if not exists orders (
  id bigserial primary key,
  reservation_id bigint not null references reservations(id) on delete cascade,
  user_id bigint not null references users(id),
  amount_cents bigint not null,
  currency varchar(16) not null,
  status varchar(16) not null default 'REQUIRES_PAYMENT',
  provider varchar(64),
  provider_payment_id varchar(128),
  created_at timestamptz not null default now()
);

create table if not exists tickets (
  id bigserial primary key,
  order_id bigint not null references orders(id) on delete cascade,
  event_id bigint not null references events(id) on delete cascade,
  ticket_code varchar(128) not null unique,
  attendee_name varchar(255),
  attendee_email varchar(255),
  checked_in_at timestamptz
);
create index if not exists idx_tickets_code on tickets(ticket_code);
