package com.generaSrpinglSecurity.spring.config;

import com.generaSrpinglSecurity.spring.ExceptionHandler.UserNameExistException;
import com.generaSrpinglSecurity.spring.entity.Token;
import com.generaSrpinglSecurity.spring.entity.User;
import com.generaSrpinglSecurity.spring.repository.TokenRepository;
import com.generaSrpinglSecurity.spring.repository.UserRespository;
import com.generaSrpinglSecurity.spring.service.jswtService.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    public AuthenticationResponse register(User request) throws UserNameExistException {
        Optional<User> existingUser = userRespository.findByUserName(request.getUsername()) ;
        if (existingUser.isPresent()) {
            throw new UserNameExistException("User already exists with username: " + request.getUsername());
        }


        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user = userRespository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // save generated jwttoken
        saveUserToken(user, accessToken);
        return new AuthenticationResponse(accessToken, refreshToken);



    }



    public AuthenticationResponse authenticate(User request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRespository.findByUserName(request.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
        String  accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RevokeAllTokensByUser(user);
        saveUserToken(user, accessToken);

        return new AuthenticationResponse(accessToken, refreshToken);

    }

    private void RevokeAllTokensByUser(User user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokenByUser(user.getId());

        if(!validTokenListByUser.isEmpty()){
            validTokenListByUser.forEach(t->{
                t.setLoggedOut(true);
            });
        }

        tokenRepository.saveAll(validTokenListByUser);
    }

    private void saveUserToken(User user, String jwttoken) {
        Token token = new Token();
        token.setToken(jwttoken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

}
