package com.clody.infra.external.feign.apple;

import com.clody.infra.external.feign.dto.response.apple.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")

public interface AppleAuthClient {
    @GetMapping(value = "/keys")
    ApplePublicKeys getApplePublicKeys();
}
