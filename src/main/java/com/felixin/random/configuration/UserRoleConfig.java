package com.felixin.random.configuration;

import com.felixin.random.domain.Role;
import com.felixin.random.dto.SingUpDTO;
import com.felixin.random.enums.RoleType;
import com.felixin.random.repository.RoleRepository;
import com.felixin.random.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class UserRoleConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    public void init() {
        Role adminRole = new Role();
        Role userRole = new Role();
        adminRole.setName(RoleType.ROLE_ADMIN);
        userRole.setName(RoleType.ROLE_USER);
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        userService.deleteAllUsers();

        SingUpDTO admin = new SingUpDTO();
        admin.setUsername("admin");
        admin.setPassword("123");
        Set<RoleType> adminRoles = new HashSet();
        adminRoles.add(RoleType.ROLE_ADMIN);
        adminRoles.add(RoleType.ROLE_USER);
        admin.setRoleType(adminRoles);

        userService.signUp(admin);

        SingUpDTO user1 = new SingUpDTO();
        user1.setUsername("user");
        user1.setPassword("123");

        Set<RoleType> userRoles = new HashSet();
        userRoles.add(RoleType.ROLE_USER);
        user1.setRoleType(userRoles);
        userService.signUp(user1);
    }

    @Bean(initMethod = "init")
    public UserRoleConfig getMailService() {
        return new UserRoleConfig();
    }
}
