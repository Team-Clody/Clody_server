package com.donkeys_today.server.support.feign.dto.response.apple;

import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplePublicKeys {
    private List<ApplePublicKey> keys;

    public ApplePublicKey getMatchesKey(String alg, String kid) {
        return keys.stream()
                .filter(applePublicKey -> applePublicKey.alg().equals(alg) && applePublicKey.kid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException(ErrorType.INVALID_ID_TOKEN));
    }
}
