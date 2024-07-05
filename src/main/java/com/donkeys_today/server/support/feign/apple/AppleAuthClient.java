package com.donkeys_today.server.support.feign.apple;

import com.donkeys_today.server.support.feign.dto.response.apple.AppleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AppleAuthClient", url = "https://appleid.apple.com", configuration = AppleFeignConfiguration.class)
public interface AppleAuthClient {

    @PostMapping(value = "/oauth/token")
    AppleTokenResponse getOauth2AccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code,
            @RequestParam("client_secret") String clientSecret

    );

}
