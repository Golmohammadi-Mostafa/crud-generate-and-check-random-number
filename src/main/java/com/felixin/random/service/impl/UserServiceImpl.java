package com.felixin.random.service.impl;

import com.felixin.random.domain.Role;
import com.felixin.random.domain.User;
import com.felixin.random.dto.JwtTokenDTO;
import com.felixin.random.dto.SingUpDTO;
import com.felixin.random.dto.UserResponseDTO;
import com.felixin.random.enums.RoleType;
import com.felixin.random.enums.UserStatus;
import com.felixin.random.exception.CustomException;
import com.felixin.random.repository.UserRepository;
import com.felixin.random.security.JwtTokenProvider;
import com.felixin.random.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;

    }

    @Override
    public JwtTokenDTO signIn(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            return new JwtTokenDTO(token);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public JwtTokenDTO signUp(SingUpDTO singUpDTO) {


        if (!userRepository.existsByUsername(singUpDTO.getUsername())) {
            User user = new User();

            Set<RoleType> roleType = singUpDTO.getRoleType();
            Set<Role> roles = new HashSet<>();
            roleType.forEach(r -> {
                Role role = new Role();
                role.setName(r);
                roles.add(role);
            });
            user.setUsername(singUpDTO.getUsername());
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(singUpDTO.getPassword()));
            User savedUser = userRepository.save(user);
            String token = jwtTokenProvider.createToken(savedUser.getUsername(), savedUser.getRoles());
            return new JwtTokenDTO(token);
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public UserResponseDTO search(String username) {
        return whoAmI(username);
    }

    @Override
    public UserResponseDTO whoAmI(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        Set<Role> roles = user.getRoles();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setRoles(roles.stream().map(r -> r.getName().name()).collect(Collectors.toSet()));
        userResponseDTO.setUsername(user.getUsername());
        return userResponseDTO;
    }

    @Override
    public JwtTokenDTO refresh(String username) {
        String token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
        jwtTokenDTO.setToken(token);
        return jwtTokenDTO;
    }

    @Override
    public UserResponseDTO changeUserStatus(String username, UserStatus userStatus) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        userRepository.save(user);
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUsername(user.getUsername());
        return responseDTO;
    }


    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
