package com.mhere.common.auth;

import com.mhere.common.http.HttpClient;
import com.mhere.common.http.ResponseSpec;
import com.mhere.common.transfer.UserTransfer;

public class AuthService {

    private static final String AUTH_SERVER_ADDRESS = "lb:/crush-auth/";

    public static TokenAuthResult authenticateByTokenString(String tokenString) {

        return HttpClient
                .create()
                .uri(AUTH_SERVER_ADDRESS + "/authentication?token=" + tokenString)
                .async()
                .get(TokenAuthResult.class)
                .map(ResponseSpec.ResponseOperationSpec::body)
                .block();
    }

    public static void createContext(UserTransfer user) {
        UserContext context = new UserContext(user);
        UserContextHolder.setContext(context);
    }

}
