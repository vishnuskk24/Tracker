package com.eng.jwtvalidation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eng.security.CustomUserDetailService;
import com.eng.security.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	CustomUserDetailService customUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			String authHeader =  request.getHeader("Authorization");
			String token =null;
			String username =null;
			
			if(authHeader!=null && authHeader.startsWith("Bearer ") ) {
				token= authHeader.substring(7);// bearer is not required
				username=jwtService.extractUserName(token);
			}                 // checking wheather it is already autheniticated
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				UserDetails userDetails = customUserDetailService.loadUserByUsername(username); // getting actual user data
//				passing both user data  and token to validate
				if(jwtService.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authtoken =  new 	UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
							
					authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authtoken); // seting the token in the Security context to prevent the further checking
					
				}
			}
					filterChain.doFilter(request, response);
		
	}

}
