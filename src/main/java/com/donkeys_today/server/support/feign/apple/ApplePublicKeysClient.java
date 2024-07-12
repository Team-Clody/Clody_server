package com.donkeys_today.server.support.feign.apple;

import com.donkeys_today.server.support.feign.dto.response.apple.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth", configuration = AppleFeignConfiguration.class)
public interface ApplePublicKeysClient {
    @GetMapping(value = "/keys")
    ApplePublicKeys getApplePublicKeys();

}