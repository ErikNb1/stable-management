package de.kisters.stablemanagement.util.security;

import de.kisters.stablemanagement.util.security.jwt.JwtRequestFilter;
import de.kisters.stablemanagement.util.security.jwt.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static de.kisters.stablemanagement.user.model.entity.Role.STABLE_OWNER;
import static de.kisters.stablemanagement.user.model.entity.Role.USER;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConf {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter requestFilter;

    public SecurityConf(JwtUserDetailsService jwtUserDetailsService, JwtRequestFilter requestFilter) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.requestFilter = requestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "%s > %s".formatted(STABLE_OWNER, USER);
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // TODO der CORS Kram muss hier noch korrekt angepasst werden.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.HEAD.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.DELETE.name()));
        corsConfiguration.setMaxAge(1800L);
        source.registerCorsConfiguration("/**", corsConfiguration); // you r


        httpSecurity.csrf().disable().cors().configurationSource(source).and()
                .authorizeHttpRequests()
                .requestMatchers(
                        antMatcher("/dev/**"),
                        antMatcher("/user/login"),
                        antMatcher("/user/refresh-token/**"),
                        antMatcher("/registration"),
                        antMatcher("/authentication")).permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jwtUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
