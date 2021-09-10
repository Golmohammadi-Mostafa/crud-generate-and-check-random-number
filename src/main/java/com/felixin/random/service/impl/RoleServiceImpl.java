package com.felixin.random.service.impl;

import com.felixin.random.domain.Role;
import com.felixin.random.enums.RoleType;
import com.felixin.random.repository.RoleRepository;
import com.felixin.random.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public Optional<Role> findByName(RoleType roleName) {
        return roleRepository.findByName(roleName);
    }
}
