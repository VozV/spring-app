package ru.edu.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.edu.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class TokenAuthenticationService {

    private static final String SECRET = "SomeSecretKey";
    private static final long EXPIRATION_TIME = 864_000_000L;
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    private final UserService userService;

    public TokenAuthenticationService(UserService userService) {
        this.userService = userService;
    }

    static void addAuthentication(HttpServletResponse response, String username) {
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + generationToken(username));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = getToken(request);


        if (token == null || token.isEmpty()) {
            return null;
        }

        String username = getUsername(token);
        UserDetails userDetails = userService.loadUserByUsername(username);

        if (userDetails == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private static String generationToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private static String getToken(HttpServletRequest request) {
        if (request.getHeader(HEADER_STRING) != null) {
            return request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX + " ", "");
        }
        else
            return null;
    }

    public static String getUsername(String token) {
        try {
            return token != null ? Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject() : null;
        } catch (JwtException e) {
            return null;
        }
    }


}
