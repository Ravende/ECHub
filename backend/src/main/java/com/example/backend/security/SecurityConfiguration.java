package com.example.backend.security;

import com.example.backend.service.LoadUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Slf4j
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private LoadUserInfoService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/","/api/**","/api/auth/**", "/css/**", "/js/**","/h2-console/**", "/map.html", "index.html").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/cafes/*/reviews").authenticated()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/api/auth/login")
                                .permitAll() // 로그인 페이지에는 누구나 접근 가능하도록 설정
                                .defaultSuccessUrl("/api/auth/success")
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/api/auth/logout")
                                .permitAll()
                );

        return http.build();
    }

    // security 비활성화 < 리다이렉션 문제 발생 시 보통 이걸로 해결 가능
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/api/**"))
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }


}
