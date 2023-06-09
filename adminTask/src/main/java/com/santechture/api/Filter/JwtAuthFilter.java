package com.santechture.api.Filter;

import com.santechture.api.service.JwtTokenUtil;
import com.santechture.api.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter
{
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

           final String authHeader = request.getHeader("Authorization");
           final String jwt;
           final String username;
           if(authHeader == null || !authHeader.startsWith("Bearer"))
           {
               filterChain.doFilter(request , response);
               return;
           }

           jwt = authHeader.substring(7);
           username = jwtTokenUtil.extractUsername(jwt);
           if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
           {
               UserDetails user = userDetailsService.loadUserByUsername(username);
               if(jwtTokenUtil.validateToken(jwt , user))
               {
                   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                           user ,
                           null,
                           user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
               }
           }
           filterChain.doFilter(request , response);
    }
}
