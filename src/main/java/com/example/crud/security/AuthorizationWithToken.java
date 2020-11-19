package com.example.crud.security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/*
    created by HuyenNgTn on 19/11/2020
*/
public class AuthorizationWithToken {
    public static final Logger logger = LoggerFactory.getLogger(AuthorizationWithToken.class);
    private static final String SECRET_KEY= "A_Xuan487152";


    public static String createJWT(long userId, String userName, String role, long mili){
        SignatureAlgorithm signatureAlgorithm= SignatureAlgorithm.ES256;

        long currentMilis= System.currentTimeMillis();
        Date now= new Date(currentMilis);

        byte[] apiKeySecretBytes= DatatypeConverter.parseBase64Binary(SECRET_KEY);

        Key signingKey= new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder jwtBuilder= Jwts.builder().setId(String.valueOf(userId)).setAudience(userName).setSubject(role).setIssuedAt(now).signWith(signatureAlgorithm, signingKey);

        if (mili > 0) {
            long expMilis= currentMilis + mili;
            Date exp= new Date(expMilis);
            jwtBuilder.setExpiration(exp);
        }

        String tmp= jwtBuilder.compact();
        System.out.println(tmp);
        return tmp;
    }

    public static void main(String[] args) {
        AuthorizationWithToken authorizationWithToken= new AuthorizationWithToken();
        authorizationWithToken.createJWT(12345l, "huyenngtn", "admin", 600000);
    }
}
