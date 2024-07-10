package com.donkeys_today.server.support.feign.apple;

import lombok.Getter;

@Getter
public class AppleIdTokenPayload {

    private String sub;

    private String email;

    private String exp;

    
}