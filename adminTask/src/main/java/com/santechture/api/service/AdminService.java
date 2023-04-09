package com.santechture.api.service;

import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.admin.AdminDto;
import com.santechture.api.entity.Admin;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.validation.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminService {


    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    public ResponseEntity<GeneralResponse> login(LoginRequest request) throws Exception {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword() ));
        final Admin admin = userDetailsService.loadUserByUsername(request.getUsername());
        if (Objects.isNull(admin) || !admin.getPassword().equals(request.getPassword())) {
            throw new BusinessExceptions("login.credentials.not.match");
        }
        final String token = jwtTokenUtil.generateToken(admin);
        return new GeneralResponse().response(new AdminDto(admin , token));
    }
}
