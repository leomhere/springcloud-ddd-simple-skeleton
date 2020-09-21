package com.mhere.auth;

public class UserContext {

    private UserTransfer user;

    public UserTransfer getUser() {
        return user;
    }

    public void setUser(UserTransfer user) {
        this.user = user;
    }

    UserContext(UserTransfer user) {
        this.user = user;
    }

    UserContext() {}

}
