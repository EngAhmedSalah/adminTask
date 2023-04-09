package com.santechture.api.dto.admin;

import com.santechture.api.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class AdminDto {

    private Integer adminId;

    private String username;

    private final String jwt;

    public AdminDto(Admin admin , String jwt){
        setAdminId(admin.getAdminId());
        setUsername(admin.getUsername());
        this.jwt = jwt;
    }

}
