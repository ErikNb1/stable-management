package de.kisters.stablemanagement.util.security.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (e instanceof UsernameNotFoundException){
            request.getRequestDispatcher("/login-nousername").forward(request, response);
        } else if (e instanceof InternalAuthenticationServiceException){
            request.getRequestDispatcher("login-notverified").forward(request, response);
        } else {
            request.getRequestDispatcher("login-failure").forward(request, response);
        }
    }
}
