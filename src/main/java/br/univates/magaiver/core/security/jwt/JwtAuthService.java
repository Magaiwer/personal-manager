package br.univates.magaiver.core.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

public class JwtAuthService {
    private final RSAKey RSA_KEY;

    public JwtAuthService(RSAKey RSA_KEY) {
        this.RSA_KEY = RSA_KEY;
    }

    public String generateToken(String username) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
        RSASSASigner signer = new RSASSASigner(RSA_KEY);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public String getUsernameFromToken(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) RSA_KEY.toPublicKey());

        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Token verification failed");
        }

        return signedJWT.getJWTClaimsSet().getSubject();
    }

    public boolean isTokenExpired(String token) throws ParseException {
        Date expirationTime = SignedJWT.parse(token).getJWTClaimsSet().getExpirationTime();
        return expirationTime.before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) RSA_KEY.toPublicKey());

            return signedJWT.verify(verifier);
        } catch (Exception e) {
            return false;
        }
    }
}