package com.burak.twitter.twitterapi.authentication;
import com.burak.twitter.twitterapi.dto.UserDtoRequest;
import com.burak.twitter.twitterapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    private  UserRepository userRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository=userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {

            System.out.println("req: "+req.getInputStream());
            UserDtoRequest creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserDtoRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        User user=(User) auth.getPrincipal();

        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                .compact();

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        Cookie cookie=new Cookie("token",token);
        //res.addCookie(cookie);

        com.burak.twitter.twitterapi.entity.User userres=userRepository.findByUsername(user.getUsername());
        res.addIntHeader("id", Math.toIntExact(userres.getId()));
        //res.addHeader("id",userres.getId().toString());
        //res.getOutputStream().write((SecurityConstants.TOKEN_PREFIX + token).getBytes());
        res.getOutputStream().println(userres.getId()+" "+SecurityConstants.TOKEN_PREFIX + token);
    }
}
