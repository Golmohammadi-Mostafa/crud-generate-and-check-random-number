package com.felixin.random.dto;

import com.felixin.random.enums.RoleType;

import java.io.Serializable;
import java.util.Set;

public class SingUpDTO implements Serializable {

    private String username;
    private String password;
    private Set<RoleType> roleType;

    public SingUpDTO(String username, String password, Set<RoleType> roleType) {
        this.username = username;
        this.password = password;
        this.roleType = roleType;
    }

    public SingUpDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleType> getRoleType() {
        return roleType;
    }

    public void setRoleType(Set<RoleType> roleType) {
        this.roleType = roleType;
    }
}
