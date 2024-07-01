package com.donkeys_today.server.support.feign.kakao;

import com.donkeys_today.server.support.feign.dto.response.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoAuthClient", url = "https://kauth.kakao.com")
public interface KakaoAuthClient {

  @PostMapping(value = "/oauth/token",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  KakaoTokenResponse getOauth2AccessToken(
      @RequestParam("grant_type") String grantType,
      @RequestParam("client_id") String client_id,
      @RequestParam("redirect_uri") String redirectUri,
      @RequestParam("code") String code
  );
}
