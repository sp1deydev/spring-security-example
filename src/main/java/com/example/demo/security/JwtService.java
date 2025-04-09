package com.example.demo.security;

import com.example.demo.exception.ApplicationException;
import com.example.demo.utils.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    private final static int ACCESS_TOKEN_EXPIRE_TIME = 5 * 60 * 1000;
//    private final static int ACCESS_TOKEN_EXPIRE_TIME = 30 * 1000;
    private final static int REFRESH_TOKEN_EXPIRE_TIME = 5 * 24 * 60 * 60 * 1000;

    private String generateToken(String subject, int expireTime) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", List.of("ADMIN", "USER"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(String username) {
        return generateToken(username, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public boolean verifyToken(String token) {
        log.info("[verifyToken] - verify token START");
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("[verifyToken] - verify token DONE");
            return true;
        }
        catch(SignatureException e) {
            log.info("[verifyToken] - verify token failed: Token is incorrect");
            throw new ApplicationException(ErrorCode.INVALID_PARAMETER, "Token is incorrect");
        }
        catch (ExpiredJwtException e) {
            log.info("[verifyToken] - verify token failed: Token is expires");
            throw new ApplicationException(ErrorCode.INVALID_PARAMETER, "Token is expires");
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
