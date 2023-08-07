package com.brock.HoCaTO.service;

import com.brock.HoCaTO.dto.UserDto;
import com.brock.HoCaTO.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
