package com.donkeys_today.server.support.security.config;

import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.security.auth.JwtAuthenticationEntryPoint;
import com.donkeys_today.server.support.security.filter.ExceptionHandlingFilter;
import com.donkeys_today.server.support.security.filter.JwtAuthenticationFilter;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
        .cors(
            corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource())
        )
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
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:8080");
    config.addAllowedOrigin("http://localhost:5173");
    config.addAllowedOrigin("https://kauth.kakao.com");
    config.addAllowedOrigin("https://kapi.kakao.com");
    config.addAllowedOrigin("http://www.googleapis.com");
    config.addAllowedOrigin("https://www.googleapis.com");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
    config.addExposedHeader("Authorization");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
