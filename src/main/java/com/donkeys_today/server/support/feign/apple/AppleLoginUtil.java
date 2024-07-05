package com.donkeys_today.server.support.feign.apple;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AppleLoginUtil {
    /**
     * client_secret 생성 Apple Document URL ‣
     * https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
     *
     * @return client_secret(jwt)
     */
    public static String createClientSecret(
            String teamId, String clientId, String keyId, String keyPath, String authUrl) {

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(keyId).build();
        JWTClaimsSet claimsSet = new JWTClaimsSet();
        Date now = new Date();

        claimsSet.setIssuer(teamId);
        claimsSet.setIssueTime(now);
        claimsSet.setExpirationTime(new Date(now.getTime() + 3600000));
        claimsSet.setAudience(authUrl);
        claimsSet.setSubject(clientId);

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(readPrivateKey(keyPath));
        try {
            KeyFactory kf = KeyFactory.getInstance("EC");
            ECPrivateKey ecPrivateKey = (ECPrivateKey) kf.generatePrivate(spec);
            JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey.getS());
            jwt.sign(jwsSigner);
        } catch (JOSEException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return jwt.serialize();
    }

    /**
     * 파일에서 private key 획득
     *
     * @return Private Key
     */
    private static byte[] readPrivateKey(String keyPath) {

        Resource resource = new ClassPathResource(keyPath);
        byte[] content = null;

        try (InputStream keyInputStream = resource.getInputStream();
             InputStreamReader keyReader = new InputStreamReader(keyInputStream);
             PemReader pemReader = new PemReader(keyReader)) {
            PemObject pemObject = pemReader.readPemObject();
            content = pemObject.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}