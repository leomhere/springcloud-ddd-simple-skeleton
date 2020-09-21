package com.mhere.auth;

import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> contextHolder = new ThreadLocal<>();

    public static void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        contextHolder.set(context);
    }

    public static UserContext getContext() {
        UserContext userContext = contextHolder.get();
        if (userContext == null){
            userContext = createEmptyContext();
            contextHolder.set(userContext);
        }
        return userContext;
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }

    private static void clearContext(){
        contextHolder.remove();
    }

}
