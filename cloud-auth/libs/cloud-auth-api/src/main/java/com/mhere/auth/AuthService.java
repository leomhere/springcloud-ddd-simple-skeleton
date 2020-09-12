package com.mhere.auth;


import com.mhere.utils.http.HttpClient;
import com.mhere.utils.http.ResponseSpec;

public class AuthService {

    private static final String AUTH_SERVER_ADDRESS = "lb:/crush-auth";

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
