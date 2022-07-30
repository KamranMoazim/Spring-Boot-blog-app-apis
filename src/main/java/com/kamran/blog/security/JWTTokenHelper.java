package com.kamran.blog.security;

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
public class JWTTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	private String secret = "ThisIsSecret";
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
	}
	
	private boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return deGenerateToken(claims, userDetails.getUsername());
	}
	
	private String deGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + this.JWT_TOKEN_VALIDITY*100))
				.signWith(SignatureAlgorithm.HS512, this.secret)
				.compact();
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = this.getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
	}
	
	
	
}






