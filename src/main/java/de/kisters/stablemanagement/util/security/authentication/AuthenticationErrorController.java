package de.kisters.stablemanagement.util.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class AuthenticationErrorController {

    @ExceptionHandler(UsernameNotFoundException.class)
    public String exception(final UsernameNotFoundException exception, final Model model) {
        log.error("Exception during execution of SpringSecurity application", exception);
        String errorMessage = (exception != null ? exception.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

}
