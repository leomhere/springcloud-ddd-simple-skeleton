package com.mhere.auth;

public class TokenAuthResult {
    public UserTransfer user;
    public TokenTransfer token;

    public UserTransfer getUser() {
        return user;
    }

    public void setUser(UserTransfer user) {
        this.user = user;
    }

    public TokenTransfer getToken() {
        return token;
    }

    public void setToken(TokenTransfer token) {
        this.token = token;
    }

    public TokenAuthResult(UserTransfer user, TokenTransfer token) {
        this.user = user;
        this.token = token;
    }
}
