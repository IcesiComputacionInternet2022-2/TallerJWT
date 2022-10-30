package com.icesi.edu.users.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JWTParser {

    public static String SECRET_KEY = "v,J#m:71[!mThdv=*$;&0+?hK'/>4,-}w!ax]='@NpX9&mw>\"``~C|6y-ZFoFs=$\"b(;D\"[g8xu6uTNyV[n}8-zTRE)SYOgoOV*}M{-";


    public static String createJWT(String id, String issuer, String subject, Map<String,String> claims, long ttlMillis){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiSecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        Key loginKey = new SecretKeySpec(apiSecretBytes,signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer).setClaims(claims).signWith(loginKey,signatureAlgorithm);

        if (ttlMillis > 0){
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }


    public static Claims decodeJWT(String jwt){
        return Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).build().parseClaimsJws(jwt).getBody();
    }

}
