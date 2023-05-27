package com.moviebookingapp.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {
	
	private static final String secretKey = "${jwt.secret}";
	private long expirationTime = 1000 * 60 * 60;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

//create the token
	private String createToken(Map<String, Object> claims, String subject) {
		String compact = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))// token valid for 60 minutes
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
		return compact;
	}

//validate the token
	public Boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
