package de.kisters.stablemanagement.user.service;

import de.kisters.stablemanagement.user.model.dto.UserDto;
import de.kisters.stablemanagement.user.model.dto.LoginDto;
import de.kisters.stablemanagement.user.model.dto.UserEditDto;
import de.kisters.stablemanagement.user.model.dto.UserRegistrationDto;
import de.kisters.stablemanagement.user.model.mapper.UserMapper;
import de.kisters.stablemanagement.user.model.entity.User;
import de.kisters.stablemanagement.user.repository.UserRepository;
import de.kisters.stablemanagement.util.exception.exception.AuthenticationException;
import de.kisters.stablemanagement.util.exception.exception.NoEntityFoundException;
import de.kisters.stablemanagement.util.security.authentication.UserAlreadyExistException;
import de.kisters.stablemanagement.util.security.jwt.JwtTokenUtil;
import de.kisters.stablemanagement.util.security.jwt.dto.JwtTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final RoleHierarchy roleHierarchy;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, PasswordEncoder encoder, RoleHierarchy roleHierarchy) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.roleHierarchy = roleHierarchy;
    }
    @Override
    public JwtTokenDto loginUser(LoginDto loginDto) {
        if (loginDto.getAccessToken() != null && !loginDto.getAccessToken().isBlank()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(loginDto.getAccessToken()));
            Boolean tokenIsValid = jwtTokenUtil.validateToken(loginDto.getAccessToken(), userDetails);
            if (tokenIsValid == null || !tokenIsValid) {
                throw new AuthenticationException("Token is not valid");
            }
            return jwtTokenUtil.generateToken(userDetails);
        } else if (loginDto.getPassword() != null && loginDto.getUserName() != null) {
            final User user = userRepository.findByEmail(loginDto.getUserName()).orElseThrow(() -> new AuthenticationException("Invalid user data"));
            log.info(passwordEncoder.encode(loginDto.getPassword()));
            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
                throw new AuthenticationException("Invalid user data");
            return jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
        } else {
            throw new AuthenticationException("Invalid user data");
        }
    }
    @Override
    public UserDto registerUser(UserRegistrationDto registrationDto) {
        if (emailExists(registrationDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + registrationDto.getEmail());
        }
        User user = userMapper.mapRegistrationToEntity(registrationDto);
        user.setPassword(encoder.encode(user.getPassword()));
        return userMapper.mapUserToDto(userRepository.save(user));
    }
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserDto getUserForToken() {
        return userMapper.mapUserToDto(getUserEntityForToken());
    }
    @Override
    public User getUserEntityForToken() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authenticationToken.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NoEntityFoundException("No data for Token"));
    }
    @Override
    public boolean hasUserRole(User user, String role) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
        return roleHierarchy.getReachableGrantedAuthorities(userDetails.getAuthorities()).stream().anyMatch(r -> r.getAuthority().equals(role));
    }
    @Override
    public JwtTokenDto refreshToken(String refreshToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(refreshToken));
        Boolean tokenIsValid = jwtTokenUtil.validateToken(refreshToken, userDetails);
        if (tokenIsValid == null || !tokenIsValid)
            throw new AuthenticationException("RefreshToken is not valid");
        return jwtTokenUtil.generateToken(userDetails);
    }
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoEntityFoundException("No User found for email " + email));
    }
    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoEntityFoundException("No user found for ID " + userId));
    }
    @Override
    public UserDto getMappedDto(User user) {
        return userMapper.mapUserToDto(user);
    }
}
