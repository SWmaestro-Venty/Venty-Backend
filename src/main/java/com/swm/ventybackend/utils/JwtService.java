package com.swm.ventybackend.utils;


import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.swm.ventybackend.config.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.swm.ventybackend.config.BaseResponseStatus.*;

@Service
public class JwtService {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;

    @Value("${JWT_REFRESH_KEY}")
    private String JWT_REFRESH_KEY;

    public String createJwt(long userId){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public Token createAccessToken(Long userId, Integer status) {
        Date now = new Date();

        // Access Token
        String accessToken = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userId)
                .claim("status", status.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userId)
                .claim("status", status.toString())
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 14)))
                .signWith(SignatureAlgorithm.HS256, JWT_REFRESH_KEY)
                .compact();

        return com.swm.ventybackend.utils.Token.builder()
                .accessToken(accessToken)
                .refershToken(refreshToken)
                .key(userId)
                .build();
    }


    public String validateRefreshToken(String refreshToken) throws BaseException {
        if(refreshToken == null || refreshToken.length() == 0) {
            throw new BaseException(EMPTY_JWT);
        }

        try {
            Jws<Claims> claims;
            claims = Jwts.parser()
                    .setSigningKey(JWT_REFRESH_KEY)
                    .parseClaimsJws(refreshToken);

            if (!claims.getBody().getExpiration().before(new Date())) {
                return recreationAccessToken(
                        claims.getBody().get("userIdx").toString(),
                        claims.getBody().get("status").toString()
                );}

        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }
        return null;
    }

    public String recreationAccessToken(String userId, String status) {
        Date now = new Date();

        // Access Token
        String accessToken = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userId)
                .claim("status", status)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();

        return accessToken;
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
//    public String getJwt(){
//        HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
//        return request.getHeader("X-ACCESS-TOKEN");
//    }

    /*
    JWT에서 userIdx 추출
    @return long
    @throws BaseException
     */
    public JwtSuccessDTO getToken(String accessToken) throws BaseException {
        JwtSuccessDTO result = new JwtSuccessDTO();

        //1. JWT 추출
//        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);

            if (!claims.getBody().getExpiration().before(new Date())) {
                System.out.println("claims.getBody().get(\"userIdx\").toString() = " + claims.getBody().get("userIdx").toString());
                System.out.println("claims.getBody().get(\"status\").toString() = " + claims.getBody().get("status").toString());
                System.out.println("claims = " + claims.getBody());
                result.setUsersId(claims.getBody().get("userIdx").toString());
                result.setStatus(claims.getBody().get("status").toString());
            }
        } catch (Exception ignored) {
            System.out.println("ignored.getMessage() = " + ignored.getMessage());
            throw new BaseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return result;
    }
}