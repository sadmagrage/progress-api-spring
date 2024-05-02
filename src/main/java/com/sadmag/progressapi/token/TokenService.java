package com.sadmag.progressapi.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sadmag.progressapi.expiration_date.ExpirationDateService;
import com.sadmag.progressapi.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private ExpirationDateService expirationDateService;

    public TokenRecord generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var tokenString = JWT.create()
                    .withIssuer("progress-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDateService.genExpirationDate((short) 15))
                    .sign(algorithm);

            return new TokenRecord(tokenString);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("progress-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    public String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
