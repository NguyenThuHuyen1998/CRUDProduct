package com.example.crud.security;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.User;
import com.example.crud.service.impl.UserServiceImpl;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/*
    created by HuyenNgTn on 19/11/2020
*/
public class AuthorizationWithToken {
    public static final Logger logger = LoggerFactory.getLogger(AuthorizationWithToken.class);
    private static String MD5_ENDCODING = "{MD5}";
    private static String SECRET_KEY= "IfqwertyullllllIfqwertyullllll12345678901234567890";
    private static long MILIS= 3*24*60*60*1000;

    private UserServiceImpl userServiceImpl;

    public String checkLogin(String userName, String password){
        User user= userServiceImpl.findByName(userName);
        if(user== null){
            return "";
        }
        if(verifyMD5(user.getPassword(), password)){
            String role= user.getRole();
            return createJWT(userName, role);

        }
        return "";

    }

    public void registry(User user){
        String purePassword= user
    }


    public String generatedMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return MD5_ENDCODING + new String(Base64.getEncoder().encode(digest));
        } catch (NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        } catch (UnsupportedEncodingException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static boolean verifyMD5(String encodedPassword, String password) {
        if (!encodedPassword.startsWith(MD5_ENDCODING)) {
            return false;
        } else {
            String generated = generatedMD5(password);
            return generated.equals(encodedPassword);
        }
    }



    public static String createJWT(String userName, String role){
        SignatureAlgorithm signatureAlgorithm= SignatureAlgorithm.HS256;

        long currentMilis= System.currentTimeMillis();
        Date now= new Date(currentMilis);

        byte[] decodedSecret = Base64.getDecoder().decode(SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(decodedSecret);
        JwtBuilder jwtBuilder= Jwts.builder().setAudience(userName).setSubject(role).setIssuedAt(now).signWith(signatureAlgorithm,key);

        long expMilis= currentMilis + MILIS;
        Date exp= new Date(expMilis);
        jwtBuilder.setExpiration(exp);

        String tmp= jwtBuilder.compact();
        System.out.println(tmp);
        return tmp;

    }

    public void decode(String token) throws ParseException {
        Base64.Decoder decoders= Base64.getUrlDecoder();
        String[] split= token.split("\\.");
        String header= new String(decoders.decode(split[0]));
        String body= new String(decoders.decode(split[1]));
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(body);
        String userName= json.getString(InputParam.USER_NAME);
        String role= json.getString(InputParam.ROLE);

        String signature= new String(decoders.decode(split[2]));

        System.out.println(header+"  "+ body+"  "+ signature);

    }

    public static void main(String[] args) throws ParseException {
        AuthorizationWithToken authorizationWithToken= new AuthorizationWithToken();
//        authorizationWithToken.createJWT("huyenngtn", "admin");
//        authorizationWithToken.decode("IfqwertyullllllIfqwertyullllll12345678901234567890eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImF1ZCI6Imh1eWVubmd0biIsInN1YiI6ImFkbWluIiwiaWF0IjoxNjA1ODg2MTU1LCJleHAiOjE2MDU4ODY3NTV9.u_15V-8tvMTqBrb_bmjqXJfJmyHlu_6uBUDmiX9dlWw");
//        String password= generatedMD5("12345");
//        System.out.println(password);

    }
}
