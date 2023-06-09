package de.kisters.stablemanagement.util.security.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kisters.stablemanagement.util.exception.exception.AuthenticationException;
import de.kisters.stablemanagement.util.exception.exception.ErrorDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@Order(1)
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUserDetailsService jwtUserDetailsService;
  private final JwtTokenUtil jwtTokenUtil;

  public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws ServletException, IOException {

    if (!request.getRequestURI().startsWith("/public") &&
            !request.getRequestURI().startsWith("/dev") &&
            !request.getRequestURI().startsWith("/doc") &&
            !request.getRequestURI().equals("/authentication") &&
            !request.getRequestURI().equals("/registration") &&
            !request.getRequestURI().equals("/user/login")) {
      try {
        setSecurityContext(request);
      } catch (AuthenticationException e) {
        log.warn("Authentication Exception: ", e);
        response.setStatus(401);
        response.getWriter().write(convertObjectToJson(new ErrorDto(e.getMessage())));
        return;
      } catch (Exception e) {
        log.error("Unhandled Exception: ", e);
        response.setStatus(400);
        response.getWriter().write(convertObjectToJson(new ErrorDto("Unknown exception")));
        return;
      }
    }
    chain.doFilter(request, response);
  }

  private void setSecurityContext(HttpServletRequest request) {
    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;
    String jwtToken = null;
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        log.warn("Unable to get JWT Token");
        throw new RuntimeException("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        log.warn("JWT Token has expired");
        throw new RuntimeException("Token is expired");
      }
    } else {
      log.warn("JWT Token does not begin with Bearer String");
      throw new RuntimeException("Invalid Token");
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

      if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
  }

  private String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }
}
