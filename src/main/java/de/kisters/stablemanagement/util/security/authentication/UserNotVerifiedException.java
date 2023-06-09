package de.kisters.stablemanagement.util.security.authentication;

import org.springframework.security.core.AuthenticationException;

public class UserNotVerifiedException extends AuthenticationException {

    public UserNotVerifiedException(String msg) {
        super(msg);
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
