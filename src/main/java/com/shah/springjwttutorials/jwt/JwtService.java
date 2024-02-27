package com.shah.springjwttutorials.jwt;

import com.shah.springjwttutorials.pojo.dto.UserSecurity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.shah.springjwttutorials.constants.MyConstants.ACCESS_TOKEN;

@Component
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token.expiration-hours}")
    private long accessTokenExpirationHours;
    @Value("${jwt.refresh-token.expiration-hours}")
    private long refreshTokenExpirationHours;

    /**
     * Generate both token type
     *
     * @param user
     * @param tokenType
     * @return
     */
    public String generateToken(UserSecurity user, String tokenType) {

        Map<String, Object> claims = Map.of(
                "Authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                "IsEnabled", user.getIsEnabled(),
                "Company",user.getCompany());

        Date exp = ACCESS_TOKEN.equalsIgnoreCase(tokenType)
                ? new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(accessTokenExpirationHours))
                : new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(refreshTokenExpirationHours));

        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(exp)
                .setIssuer("Norulshahlam & Co.")
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .addClaims(claims)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}