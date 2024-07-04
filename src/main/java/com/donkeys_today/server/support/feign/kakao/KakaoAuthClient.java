package com.donkeys_today.server.support.feign.kakao;

import com.donkeys_today.server.support.feign.dto.response.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoAuthClient", url = "https://kauth.kakao.com", configuration = KakaoFeignConfiguration.class)
public interface KakaoAuthClient {

    @PostMapping(value = "/oauth/token")
    KakaoTokenResponse getOauth2AccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code
    );
}
