package com.mhere.common.auth;

import com.mhere.common.err.Errors;
import com.mhere.common.transfer.UserTransfer;
import java.util.Optional;

public class CurrentUser {

    private UserTransfer user;
    private boolean authenticated;

    public UserTransfer getAuthentication() {
        return Optional.ofNullable(user)
                .orElseThrow(() -> Errors.UN_AUTHORIZED.create());

    }

    public UserTransfer getAuthenticationAllowAnonymous() {
        return user;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public boolean unAuthenticated() {
        return !authenticated;
    }

    public CurrentUser() {
        UserTransfer user = UserContextHolder.getContext().getUser();
        if (user == null) {
            this.user = null;
            this.authenticated = false;
        } else {
            this.user = user;
            this.authenticated = true;
        }
    }
}
