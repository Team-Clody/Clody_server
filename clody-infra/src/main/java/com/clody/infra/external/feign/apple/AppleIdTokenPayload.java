package com.clody.infra.external.feign.apple;

import lombok.Getter;

@Getter
public class AppleIdTokenPayload {

    private String sub;

    private String email;

    private String exp;

    
}
