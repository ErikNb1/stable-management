package de.kisters.stablemanagement.util.security.jwt;

import de.kisters.stablemanagement.util.security.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.time.Duration.ofMillis;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.ofOffset;
import static java.util.Date.from;

@Component
public class JwtTokenUtil {

  private static final ZoneId UTC = ofOffset("UTC", ZoneOffset.ofHours(0));
  private final Duration validity;
  private final Duration refreshValidity;
  private final String secret;


  public JwtTokenUtil(@Value("${sm.jwt.validity}") Duration validity, @Value("${sm.jwt.refresh-validity}") Duration refreshValidity, @Value("${sm.jwt.secret}") String secret) {
    this.validity = validity;
    this.refreshValidity = refreshValidity;
    this.secret = secret;
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public LocalDateTime getIssuedAtDateFromToken(String token) {
    return ofInstant(getClaimFromToken(token, Claims::getIssuedAt).toInstant(), UTC);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build();
    return jwtParser.parseClaimsJws(token).getBody();
  }

  public JwtTokenDto generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    JwtBuilder jwtBuilder = doGenerateToken(claims, userDetails.getUsername(), validity);
    String compact = jwtBuilder.compact();
    Claims claimsFromToken = getAllClaimsFromToken(compact);
    JwtTokenDto jwtTokenDto = new JwtTokenDto();
    jwtTokenDto.setAccessToken(compact);
    jwtTokenDto.getAuthentication().setRefreshToken(generateRefreshToken(userDetails));
    jwtTokenDto.getAuthentication().setAccessToken(compact);
    jwtTokenDto.getAuthentication().getPayload().setIat(convertDateToLocalDateTime(claimsFromToken.getIssuedAt()));
    jwtTokenDto.getAuthentication().getPayload().setJti(claimsFromToken.getId());
    jwtTokenDto.getAuthentication().getPayload().setSub(userDetails.getUsername());
    jwtTokenDto.getAuthentication().getPayload().setExp(convertDateToLocalDateTime(claimsFromToken.getExpiration()));
    return jwtTokenDto;
  }

  private JwtBuilder doGenerateToken(Map<String, Object> claims, String subject, Duration validity) {
    Key key = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(from(now().atZone(UTC).toInstant()))
            .setExpiration(from(now().plus(ofMillis(validity.toMillis())).atZone(UTC).toInstant()))
            .setIssuer("stable-management")
            .signWith(key);
  }

  public String generateRefreshToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    JwtBuilder jwtBuilder = doGenerateToken(claims, userDetails.getUsername(), refreshValidity);
    return jwtBuilder.compact();
  }

  private LocalDateTime convertDateToLocalDateTime(Date date) {
    Instant instant = date.toInstant();
    ZoneId zoneId = ZoneId.systemDefault();
    return LocalDateTime.ofInstant(instant, zoneId);
  }

  public Boolean canTokenBeRefreshed(String token) {
    return (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  private Boolean isTokenExpired(String token) {
    final LocalDateTime expiration = getExpirationDateFromToken(token);
    return expiration.isBefore(now());
  }

  private Boolean ignoreTokenExpiration(String token) {
    // here you specify tokens, for that the expiration is ignored
    return false;
  }

  public LocalDateTime getExpirationDateFromToken(String token) {
    return ofInstant(getClaimFromToken(token, Claims::getExpiration).toInstant(), UTC);
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

}
