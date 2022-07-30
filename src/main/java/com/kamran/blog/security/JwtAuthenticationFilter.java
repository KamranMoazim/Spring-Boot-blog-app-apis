package com.kamran.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		

		String requestToken = request.getHeader("Authorization");
		
		String token = null;
		String username = null;
		
//		getting the token
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			
			try {				
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("UNABLE TO GET JWT TOKEN !");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT TOKEN HAS EXPIRED!");
			} catch (MalformedJwtException e) {
				System.out.println("JWT TOKEN IS INVALID!");
			}
			
			
		} else {
			System.out.println("JWT TOKEN NOT FOUND or TOKEN DO NOT BEGIN WITH BEARER !");
		}
		
//		validating token
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				
//				setting authentication 
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			} else {
				System.out.println("JWT TOKEN IS INVALID!");
			}
			
		} else {
			System.out.println("USERNAME IS NULL or CONTEXT IS NOT NULL !!");
		}
		
		filterChain.doFilter(request, response);
		
	}

}













