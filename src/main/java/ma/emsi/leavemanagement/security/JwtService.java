package ma.emsi.leavemanagement.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secretkey}")
    private String secret;
    @Value("${application.security.jwt.secretkey.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refreshtoken.expiration}")
    private long refreshExpiration;


    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){

        return  generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken( Map<String,Object> extractClaims,
                                 UserDetails userDetails){

         return buildToken(extractClaims,userDetails,jwtExpiration);
    }
    public String generateRefreshToken(UserDetails userDetails){

        return  generateToken(new HashMap<>(),userDetails);
    }

    public String generateRefreshToken( Map<String,Object> extractClaims,
                                 UserDetails userDetails){

        return buildToken(extractClaims,userDetails,refreshExpiration);
    }

    private String buildToken(
            Map<String,Object> extractClaims,
            UserDetails userDetails,
            long expiration

    ){
        Instant now = Instant.now();
        Instant expirationTime = now.plus(10, ChronoUnit.MINUTES);
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+20))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String extractUsername(String jwt) {
//        return null;
//
//    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
