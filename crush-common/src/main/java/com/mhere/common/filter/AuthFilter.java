package com.mhere.common.filter;

import com.mhere.common.auth.AuthService;
import com.mhere.common.auth.TokenAuthResult;
import com.mhere.common.transfer.UserTransfer;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String tokenString = request.getHeader("Token");

        if (tokenString == null || "".equals(tokenString)) {
            chain.doFilter(request, response);
            return;
        }

        TokenAuthResult info = AuthService.authenticateByTokenString(tokenString);
        UserTransfer user = info.user;
        if (user == null) {
            chain.doFilter(request, response);
//            needAuthentication(request, response, chain);
            return;
        }
        AuthService.createContext(user);

//        HttpSession session = request.getSession(false);
//        String sessionId = session != null ? session.getId() : null;
        chain.doFilter(request, response);
    }
}
