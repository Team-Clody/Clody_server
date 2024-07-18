package com.donkeys_today.server.support.jwt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtConstants {

    public static final String USER_ID = "userId";
    public static final String TOKEN_TYPE = "type";
    public static final String ACCESS_TOKEN = "access";
    public static final String REFRESH_TOKEN = "refresh";
    public static final String REFRESH_TOKEN_PREFIX = "refreshToken:";
    public static final String KAKAO_AUTHORIZATION_PARAM = "authorization_code";
    public static final String ROLE = "role";
    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 90 * 1000L; // 1분 30초
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 1000L * 60 * 24 * 7 * 2; // 2주일

}
