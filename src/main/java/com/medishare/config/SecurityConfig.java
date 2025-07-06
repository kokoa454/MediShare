package com.medishare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/",
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/register_account",
                                "/login/reset_password",
                                "/reset_password",
                                "/about_app",
                                "/manifest.json",
                                "/callback"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login_error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .rememberMe(r -> r
                        .key("your-remember-key")
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(60 * 60 * 24 * 14) // valid for 14 days
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/callback")
                );
                
        return http.build();
        }
}
