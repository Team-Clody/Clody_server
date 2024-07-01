package com.donkeys_today.server.support.feign.config;

import com.donkeys_today.server.DonkeyServerApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = DonkeyServerApplication.class)
public class FeignConfig {
}
