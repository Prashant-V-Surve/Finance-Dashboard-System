package com.zorvyn.finance_dashboard_system.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthrizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
            )
            throws ServletException, IOException{

        //Get Authorization Header
        String authorizationHeader = request.getHeader("Authorization");

        // If no token -> continue
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        //Extract Token
        String Token = authorizationHeader.substring(7);

        try{
            //  Extract data from token
            Long userId = jwtService.extractUserId(Token);
            String role = jwtService.extractRole(Token);

            request.setAttribute("userId", userId);

            System.out.println("DEBUG → userId: " + userId);
            System.out.println("DEBUG → role from token: " + role);

            if(userId != null && jwtService.isValid(Token, userId)){

                //Create Authorities here

                var authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

                // Create Authentication object
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                authorities
                        );

                // Set into Security Context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e){
            throw e;
        }

        //continue filter chain
        chain.doFilter(request, response);
    }
}

// JWT Token -> Extract role -> Convert to Role_* -> Set in Authentification