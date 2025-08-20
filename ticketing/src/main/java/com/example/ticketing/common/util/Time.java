package com.example.ticketing.common.util;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public final class Time {
    private Time() {}
    private static Clock clock = Clock.systemUTC();
    public static Instant now() { return Instant.now(clock); }
    public static void setClock(Clock c) { clock = c; }
    public static ZoneId utc() { return ZoneId.of("UTC"); }
}
