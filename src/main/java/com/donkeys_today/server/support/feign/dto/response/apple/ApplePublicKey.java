package com.donkeys_today.server.support.feign.dto.response.apple;

public record ApplePublicKey(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e) {
}

