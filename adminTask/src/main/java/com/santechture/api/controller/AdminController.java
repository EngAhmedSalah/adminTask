package com.santechture.api.controller;


import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.service.AdminService;
import com.santechture.api.validation.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> login(@RequestBody LoginRequest request) throws Exception {
        try {
            return adminService.login(request);
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect user or password" , ex);
        }
    }
}
