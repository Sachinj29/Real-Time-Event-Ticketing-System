package com.example.ticketing.common.util;

public final class RequestContext {
    private RequestContext() {}
    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();
    public static void setUserId(String id) { USER_ID.set(id); }
    public static String getUserId() { return USER_ID.get(); }
    public static void clear() { USER_ID.remove(); }
}
