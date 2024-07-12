package com.donkeys_today.server.support.feign.dto.response.kakao;

public record KakaoTokenResponse(
        String access_token,
        String refresh_token
) {
    private static final String BEARER = "Bearer ";

    public static KakaoTokenResponse createAccessToken(String accessToken, String error) {
        return new KakaoTokenResponse(accessToken, error);
    }

    public static String getTokenWithPrefix(String accessToken) {
        return BEARER + accessToken;
    }

}
