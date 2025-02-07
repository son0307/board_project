package com.son.board.config;

import com.son.board.oauth2.service.CustomOAuth2SuccessHandler;
import com.son.board.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()   // resources 접근 허용
                    .requestMatchers("/post/**", "/user/**", "/comments/**").authenticated()   // 'write', 'user', 'comments'로 시작하는 url은 인증 필요
                    .anyRequest().permitAll()   // 그 외에는 접근 허가
                )
                .formLogin(form -> form
                    // 로그인 페이지 url (GET)
                    .loginPage("/login")
                    // 로그인 처리 url (POST)
                    .loginProcessingUrl("/login")
                    // 로그인 성공 후 이동할 url
                    .defaultSuccessUrl("/")
                    // 로그인 실패 시 이동할 url
                    .failureUrl("/login?error")
                    .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                            .userService(customOAuth2UserService)
                        )
                        .successHandler(customOAuth2SuccessHandler)
                );

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
