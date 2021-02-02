package com.fabhotels.walletsystem.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fabhotels.walletsystem.dao.UserDao;
import com.fabhotels.walletsystem.models.entity.User;
import com.fabhotels.walletsystem.utils.Constants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDao userDao;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDao userDao) {
        super(authenticationManager);
        this.userDao = userDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(Constants.AUTHORIZATION_HEADER);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHORIZATION_HEADER)
                .replace(Constants.TOKEN_PREFIX,"");

        if (token != null) {
            // parse the token and validate it
            String userName = JWT.require(Algorithm.HMAC512(Constants.API_SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            // Search in the DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (userName != null) {
                Optional<User> optionalUserUser = userDao.findUserByUsername(userName);
                if(optionalUserUser.isEmpty())
                    return null;
                UserPrincipal principal = new UserPrincipal(optionalUserUser.get());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName,
                        null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
}
