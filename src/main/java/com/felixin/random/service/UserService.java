package com.felixin.random.service;

import com.felixin.random.dto.JwtTokenDTO;
import com.felixin.random.dto.SingUpDTO;
import com.felixin.random.dto.UserResponseDTO;
import com.felixin.random.enums.UserStatus;

public interface UserService {
    JwtTokenDTO signIn(String username, String password);

    JwtTokenDTO signUp(SingUpDTO dto);

    void delete(String username);

    UserResponseDTO search(String username);

    UserResponseDTO whoAmI(String userName);

    JwtTokenDTO refresh(String username);

    UserResponseDTO changeUserStatus(String username, UserStatus userStatus);

    void deleteAllUsers();
}
