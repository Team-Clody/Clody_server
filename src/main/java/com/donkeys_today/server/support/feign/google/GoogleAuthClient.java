package com.donkeys_today.server.support.feign.google;

import com.donkeys_today.server.support.feign.dto.response.google.GoogleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GoogleAuthApiClient", url = "https://oauth2.googleapis.com/token", configuration = GoogleFeignConfiguration.class)
public interface GoogleAuthClient {

    @PostMapping
    GoogleTokenResponse getOauth2AccessToken(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "clientId") String clientId,
            @RequestParam(name = "clientSecret") String clientSecret,
            @RequestParam(name = "redirectUri") String redirectUri,
            @RequestParam(name = "grantType") String grantType);

}
