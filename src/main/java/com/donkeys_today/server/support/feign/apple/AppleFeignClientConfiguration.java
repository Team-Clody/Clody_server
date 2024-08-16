package com.donkeys_today.server.support.feign.apple;

import com.donkeys_today.server.support.feign.dto.response.apple.AppleFeignClientErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class AppleFeignClientConfiguration {

    @Bean
    public AppleFeignClientErrorDecoder appleFeignClientErrorDecoder() {
        return new AppleFeignClientErrorDecoder(new ObjectMapper());
    }
}
