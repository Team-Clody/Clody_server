package com.clody.support.constants;

import java.util.List;

public class WhiteListConstants {

    public static final List<String> FILTER_WHITE_LIST = List.of(
            "/api/v1/auth/signup",
            "/api/v1/auth/signin",
            "/api/v1/auth/reissue"
//            "/api/v1/auth/redirect"
    );

    public static final String[] SECURITY_WHITE_LIST = {
            "/api/v1/auth/signup",
            "/api/v1/auth/signin",
            "/api/v1/auth/reissue",
            "/api/v1/auth/redirect",
            "/favicon.ico",
            "/actuator/**",
            "/redirect",
            /*
            ** Swagger 관련 URL
             */
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swagger",
            "/swagger-ui/**",
            "/swagger-ui/index.html"
    };
}
