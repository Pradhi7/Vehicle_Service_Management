package com.examly.springapp.config;
 
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
 
 
@Component
public class JwtUtils {
 
    private static final String SECRET_KEY = "thisismysecretkey";
    private Date CURRENT_DATE = new Date();
    private Date EXPIRATION_DATE = new Date((System.currentTimeMillis() + (24*60*60*1000)));
 
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
 
   
    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }
 
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
 
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
 
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(CURRENT_DATE);
    }
 
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
 
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,username);
    }
 
    private String createToken(Map<String, Object> claims, String username){
        return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(CURRENT_DATE)
                    .setExpiration(EXPIRATION_DATE)
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
    }
   
}
 
 