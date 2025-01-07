//package com.eng.jwtvalidation;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Supplier;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.eng.api.ErrorApi;
//import com.eng.exception.InfyTrackerException;
//import com.eng.service.LoginService;
//import com.eng.utility.UtilityMethods;
//
//@RestController
//public class JwtUtil extends OncePerRequestFilter {
//    @Autowired
//    Environment env;
//    @Autowired
//    ErrorApi er;
//    @Autowired
//    private LoginService myUserDetailsService;
//    
//    @Autowired
//    private UtilityMethods utilityMethods;
//    
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        try {
//            System.out.println("inside do filter of Request filter xxxxxxxxxxxx");
//            final String authorizationHeader = request.getHeader("Authorization");
//            String username = null;
//            String jwt = null;
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                jwt = authorizationHeader.substring(7);
//                System.out.println("extracting username");
//                username = utilityMethods.extractUsername(jwt);
//            }
//              
//            System.out.println("line 59 username Extraction is done");
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                
//                    System.out.println("calling service to load the user to validate the token xxxxxxxxxxxx");
//                    
//                    UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
//                    System.out.println("xxxxxxxxxxxx user detaisl os loaded from userdetails");
//                    if (utilityMethods.validateToken(jwt, userDetails)) {
//                        System.out.println("inside the if block becoz the token is valid");
//                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                                userDetails, null, userDetails.getAuthorities());
//                        usernamePasswordAuthenticationToken
//                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                    }
//                    System.out.println("After if block inthe token validation  request filter dofilter");
//                
//            }                
//        }catch(UsernameNotFoundException usernameNotfoundException) {
//            System.out.println("username not found");
//            response.getWriter().write(env.getProperty(usernameNotfoundException.getMessage()));
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            return;
//            
//        }
//        catch(AccessDeniedException servletException) {
//            System.out.println(" catch block  +++++++++++++ access denied");
//            System.out.println(env.getProperty(servletException.getMessage()));
//            response.getWriter().write(env.getProperty(servletException.getMessage()));
//            response.setStatus(HttpStatus.FORBIDDEN.value());
////            response.setHeader
////            response.setHeader("TimeStamp", LocalDateTime.now().toString())
//            Map<String, String> map  = new HashMap<>();
//            map.put("message", env.getProperty(servletException.getMessage()));
//            map.put("statuscode", "403");
//            map.put("TimeStamp", LocalDateTime.now().toString());
////            sUPPLIER
//            Supplier<Map<String,String>> s = ()->map;
//            response.setTrailerFields(s);
////            response.getWriter().
//            return;
//        }
//    System.out.println("line 97 in request filter");
//    
//        chain.doFilter(request, response);
//    }
//}
//package com;
package com;



