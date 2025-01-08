package com.tracker.jwtvalidation;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

//	static KeyGenerator keyGen ;
//	 static SecretKey secretKey;//=keyGen.generateKey();
//	 static String key;
	
//	 static {
//		 try {
//			keyGen =KeyGenerator.getInstance("HmacSHA256");
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 secretKey=keyGen.generateKey();
//		 key =Base64.getEncoder().encodeToString(secretKey.getEncoded()); ;//= "Ackjvkbfjdks456789dfghvjbnftghb";
//	 }
//	JwtService() throws Exception {
		
//	}

	@Autowired
	Environment environment;
	
//
//		public static String genKey() throws Exception {
////			String key="";
//			
//
//				
////				SecretKey secretKey = keyGen.generateKey();
////				String key = 
////
////				return key;
//			return key;
//			
//		}
	public String generateToken(EmployeeDTO Employee) throws Exception {

		// inside the token body we will hav the issue data expiry date subject all the
		// stuffs
//		for that we are taking map to store in key value body
		long dueTime = 60 * 60 * 60 * 24 * 7;
		Map<String, Object> body = getBody(Employee);
		// return for i week
		System.out.println("generating key -> ");
//		if(key=="")key=genKey();
		String token = Jwts.builder().setClaims(body).setSubject(Employee.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))

				.setExpiration(new Date(System.currentTimeMillis() + dueTime)).signWith( SignatureAlgorithm.HS256,environment.getProperty("Secret_Key")).compact();
//				.expiration(new Date(System.currentTimeMillis() + dueTime)).signWith(getKey()).compact();

		return token;
	}

	private Map<String, Object> getBody(EmployeeDTO employee) {

		Map<String, Object> body = new HashMap<>();
		body.put("role", employee.getRole().toString());
		body.put("id", employee.getEmployeeId());
		body.put("name", employee.getEmployeeName());
		System.out.println("map - > " + body);
		return body;
	}

//	public SecretKey getKey(String key) {
//		if(this.secretKey==null) {
//		byte[] keybytes = Decoders.BASE64.decode(key);
//		this.secretKey= Keys.hmacShaKeyFor(keybytes);
//		return this.secretKey;
//		}
//		else return this.secretKey;
//
//	}

	public String extractUserName(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	 public  EmployeeDTO getEmployeeDetailFromJWT(String token) {
	        // Parse the token and retrieve the claims
		 return getEmployeeDetailFromMap(extractAllClaims(token));
	    }

	private Claims extractAllClaims(String token) {
		System.out.println("token --> " + token);
		
		return Jwts.parser().setSigningKey(environment.getProperty("Secret_Key")).parseClaimsJws(token).getBody();
//				.verifyWith(secretKey)
//				.build()
//				.parseSignedClaims(token)
//				.getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		System.out.println();
		return username.equals(userDetails.getUsername())&& !isTokenExpired(token);
		
	}
	public  EmployeeDTO getEmployeeDetailFromMap(Map<String,Object> pairs) {
//      {sub=neena_mc, role=Manager, name=Neena mary cyriac, id=8037, exp=1699618181, iat=1699445381}
      EmployeeDTO e = new EmployeeDTO();
      
      e.setMailId(pairs.get("sub").toString());
      e.setEmployeeId(Integer.parseInt(pairs.get("id").toString()));
      e.setRole(Role.valueOf(pairs.get("role").toString()));
      e.setEmployeeName(pairs.get("name").toString());
      e.setUsername(pairs.get("sub").toString());
      return e;
      
  }

	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	}
}
