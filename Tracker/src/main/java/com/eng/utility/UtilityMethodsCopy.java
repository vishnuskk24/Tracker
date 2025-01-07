//package com.eng.utility;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.impl.crypto.MacProvider;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
//import org.springframework.core.env.Environment;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import com.eng.dto.EmployeeDTO;
//import com.eng.entity.Role;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hazelcast.jet.JetInstance;
//
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//@Service
//public class UtilityMethods {
//	
//    @Autowired
//    Environment env;
//    
//     private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
//        private static final byte[] secretBytes = secret.getEncoded();
//     private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
////    @Autowired
////    private JetInstance jetInstance;
////    private String SECRET_KEY = env.getProperty("Secret_Key");
//    public String extractUsername(String token) {
//    	System.out.println("line 40 in utilitymthods extracting username");
//        return extractClaim(token, Claims::getSubject);
//    }
//    
//    public Integer extractEmployeeId(String token) {
//        
//        return Integer.parseInt(extractClaim(token, Claims::getId));
//    }
//    
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//    
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//    	
//    	System.out.println("extracting claims resolver ' line 54 in UM");
//        final Claims claims = extractAllClaims(token);
//        System.out.println("Extracted All claims line 57 in UM");
//        return claimsResolver.apply(claims);
//    }
//    private Claims extractAllClaims(String token) {
//    	System.out.println("Extracting all claims line 61 UM");
//        return Jwts.parser().setSigningKey(env.getProperty("Secret_Key")).parseClaimsJws(token).getBody();
//    }
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername());
//    }
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1800_000))
//                .signWith(SignatureAlgorithm.HS256, env.getProperty("Secret_Key")).compact();
//    }
//    
//    public  String getValuesFromJwt(String jwt,String key) throws Exception {
//        System.out.println("getting  values from jwt in utility methods");
//        System.out.println("JWT :"+jwt.substring(7));
//        System.out.println(Jwts.parser().parsePlaintextJwt(jwt.substring(7)));
//        System.out.println("returing keys in  in utility methods in get value method");
//        Claims claims = Jwts.parser()
//                .setSigningKey(base64SecretBytes)
//                .parseClaimsJws(jwt.substring(7)).getBody();
//
//        System.out.println(claims + " printing claims  --------");
//        return key;
//    }
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        System.out.println( "checking the token is valid or not   XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
//        final String username = extractUsername(token);
//        EmployeeDTO e = getMapFromJwt(token, username);
//        boolean status = userDetails.isEnabled();
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && status);
//    }
//    public EmployeeDTO getMapFromJwt(String substring, String string) {
//        System.out.println("taking  map from jwt token ");
//        Claims claim = Jwts.parser().setSigningKey( env.getProperty("Secret_Key"))
//                
//                .parseClaimsJws(substring).getBody();
//        return getEmployeeDetailFromMap(new HashMap<>(claim));
//        // TODO Auto-generated method stub
//        
//    }
//    public  EmployeeDTO getEmployeeDetailFromMap(Map<String,Object> pairs) {
////        {sub=neena_mc, role=Manager, name=Neena mary cyriac, id=8037, exp=1699618181, iat=1699445381}
//        EmployeeDTO e = new EmployeeDTO();
//        
//        e.setMailId(pairs.get("sub").toString());
//        e.setEmployeeId(Integer.parseInt(pairs.get("id").toString()));
//        e.setRole(Role.valueOf(pairs.get("role").toString()));
//        e.setEmployeeName(pairs.get("name").toString());
//        e.setUsername(pairs.get("sub").toString());
//        return e;
//        
//    }
//    public EmployeeDTO getEmployeeDetailFromJWT(String jwt) {
//        
//        return getMapFromJwt(jwt.substring(7),"");
//    }
//}
//package com;


