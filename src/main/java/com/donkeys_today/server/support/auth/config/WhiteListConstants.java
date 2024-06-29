package com.donkeys_today.server.support.auth.config;

import java.util.List;

public class WhiteListConstants {
    public static final List<String> FILTER_WHITE_LIST = List.of(
            "/login/oauth2/code/kakao",
            "/login/oauth2/code/google",
            "/api/v1",
            "/login",
            "/oauth/authorize",
            "/actuator/health",
            "/error",
            "/swagger-ui/",
            "/swagger-resources/",
            "/api-docs/"
    );

    public static final String[] SECURITY_WHITE_LIST = {
            "/login/**",
            "/",
            "/actuator/**"
    };
}
