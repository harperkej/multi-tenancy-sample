package com.abidat.user.service;

import com.abidat.user.dto.UserDto;

import java.util.List;

/**
 * Created by a.kuci on 4/27/2017.
 */
public interface UserService {

    public UserDto createUser(UserDto userDto);

    public List<UserDto> getAllUsers();

}
