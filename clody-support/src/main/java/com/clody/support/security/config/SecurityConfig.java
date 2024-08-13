package com.clody.support.security.config;

import com.clody.support.constants.WhiteListConstants;
import com.clody.support.jwt.JwtProvider;
import com.clody.support.security.auth.JwtAuthenticationEntryPoint;
import com.clody.support.security.filter.ExceptionHandlingFilter;
import com.clody.support.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  private final JwtProvider jwtProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    return http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        )
        .authorizeHttpRequests(
            auth -> auth.requestMatchers(WhiteListConstants.SECURITY_WHITE_LIST).permitAll()
                .anyRequest().authenticated()
        )
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new ExceptionHandlingFilter(), JwtAuthenticationFilter.class)
        .build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
    return web -> web.ignoring()
        // error endpoint를 열어줘야 함, favicon.ico 추가!
        .requestMatchers("/error", "/favicon.ico");
  }
}
