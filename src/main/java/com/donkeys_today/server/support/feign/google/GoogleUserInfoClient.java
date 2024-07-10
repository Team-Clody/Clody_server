package com.donkeys_today.server.support.feign.google;

import com.donkeys_today.server.support.feign.dto.response.google.GoogleUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "GoogleApiClient", url = "https://www.googleapis.com")
public interface GoogleUserInfoClient {

    @GetMapping("/oauth2/v3/userinfo")
    GoogleUserInfoResponse getUserInformation(
            @RequestHeader("Authorization") String token
    );

}
