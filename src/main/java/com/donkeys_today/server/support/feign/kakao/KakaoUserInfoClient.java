package com.donkeys_today.server.support.feign.kakao;

import com.donkeys_today.server.support.feign.dto.response.KakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoUserInfoClient", url = "https://kapi.kakao.com")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me")
    KakaoUserInfoResponse getUserInformation(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}
