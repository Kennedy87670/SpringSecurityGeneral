package com.generaSrpinglSecurity.spring.config;

import com.generaSrpinglSecurity.spring.ExceptionHandler.CustomAccessDeniedHandler;
import com.generaSrpinglSecurity.spring.filter.JwtAuthFilter;
import com.generaSrpinglSecurity.spring.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserServiceImpl userService;
    private final JwtAuthFilter jwtAuthFilter;
//    private final CustomLogOutHandler logoutHandler ;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

      @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers("/api/v1/auth/login", "/api/v1/auth/register")
                                .permitAll()
                                .requestMatchers("/api/v1/auth/demo")
                                .hasAnyAuthority("USER")
                                .requestMatchers("/api/v1/auth/admin_only")
                                .hasAnyAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userService)

                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))) .logout(l -> l
                        .logoutUrl("/api/v1/auth/logout")
//                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            SecurityContextHolder.clearContext();
                            // Add logging for successful logout
                            System.out.println("Logout successful");
                        }).permitAll()
                        .logoutUrl("/login?logout")
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}


