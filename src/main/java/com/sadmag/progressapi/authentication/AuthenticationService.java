package com.sadmag.progressapi.authentication;

import com.sadmag.progressapi.token.TokenRecord;
import com.sadmag.progressapi.token.TokenService;
import com.sadmag.progressapi.user.LoginRecord;
import com.sadmag.progressapi.user.RegisterRecord;
import com.sadmag.progressapi.user.User;
import com.sadmag.progressapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public TokenRecord login(LoginRecord loginRecord) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRecord.username(), loginRecord.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public void register(RegisterRecord registerRecord) {
        if (userRepository.findByUsername(registerRecord.username()) != null) throw new RuntimeException("User already registered");

        var encryptedPassword = new BCryptPasswordEncoder().encode(registerRecord.password());

        var user = new User();

        user.setUsername(registerRecord.username());
        user.setPassword(encryptedPassword);
        user.setRole(registerRecord.role());

        userRepository.save(user);
    }
}
