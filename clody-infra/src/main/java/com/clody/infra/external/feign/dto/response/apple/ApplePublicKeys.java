package com.clody.infra.external.feign.dto.response.apple;

import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.UnauthorizedException;
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
