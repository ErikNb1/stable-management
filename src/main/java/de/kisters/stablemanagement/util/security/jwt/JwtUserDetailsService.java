package de.kisters.stablemanagement.util.security.jwt;

import de.kisters.stablemanagement.util.exception.exception.AuthenticationException;
import de.kisters.stablemanagement.user.model.entity.User;
import de.kisters.stablemanagement.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private List<org.springframework.security.core.userdetails.User> users = new ArrayList<>();
  public JwtUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void initialize() {
    setUsers();
    log.info("Initialized User list with {} entries.", users.size());
  }

  @Scheduled(fixedRate = 300000)
  public void setUsers() {
    List<User> all = userRepository.findAll();
    this.users = all.stream().map(this::mapUser).toList();
  }

  private org.springframework.security.core.userdetails.User mapUser(User user) {
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities(List.of("ROLE_" + user.getRole().toString())));
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<org.springframework.security.core.userdetails.User> optionalUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    return optionalUser.orElseThrow(() -> new AuthenticationException("No User found for name: " + username));
  }
}

