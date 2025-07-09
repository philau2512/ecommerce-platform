package com.example.ecommerceplatform.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/register",
                                "/products", "/product/**",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()

                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // ✅ thêm dòng này
                        .requestMatchers("/cart/**", "/order/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            // Lấy role của user sau khi đăng nhập
                            var authorities = authentication.getAuthorities();

                            for (var authority : authorities) {
                                String role = authority.getAuthority();

                                if (role.equals("ROLE_ADMIN")) {
                                    response.sendRedirect("/admin/dashboard");
                                    return;
                                } else if (role.equals("ROLE_USER")) {
                                    response.sendRedirect("/products");
                                    return;
                                }
                            }

                            // Nếu không có role phù hợp thì quay lại trang login
                            response.sendRedirect("/login?error");
                        })
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
