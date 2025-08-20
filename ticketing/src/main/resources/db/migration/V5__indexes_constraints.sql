create index if not exists idx_events_start on events(start_at);
create index if not exists idx_events_venue on events(venue_id);
create index if not exists idx_orders_user on orders(user_id);
