package com.whapp.ssotoken.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whapp.ssotoken.config.AppProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Autowired
	AppProperties appProps;

	public String generateToken(String username, Map<String, Object> claims) {		
		return Jwts.builder()
				.setClaims(claims!=null?claims:Jwts.claims())
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + appProps.getTokenExpiration() * 1000))
				.signWith(SignatureAlgorithm.HS512, appProps.getTokenSecret()).compact();
	}	

	public boolean validateToken(String token, List<String> errorList) {
		try {
			Jwts.parser().setSigningKey(appProps.getTokenSecret()).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			errorList.add("Invalid JWT signature");		
		} catch (MalformedJwtException e) {
			errorList.add("Invalid JWT token");	
		} catch (ExpiredJwtException e) {
			errorList.add("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			errorList.add("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			errorList.add("JWT claims string is empty");
		}
		return false;
	}

	public Map<String, Object> getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(appProps.getTokenSecret()).parseClaimsJws(token).getBody();
	}
	
	public String getUserNameFromToken(String token) {
		return Jwts.parser().setSigningKey(appProps.getTokenSecret()).parseClaimsJws(token).getBody().getSubject();
	}
}