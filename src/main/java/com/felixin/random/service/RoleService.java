package com.felixin.random.service;

import com.felixin.random.domain.Role;
import com.felixin.random.enums.RoleType;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleType roleName);
}
