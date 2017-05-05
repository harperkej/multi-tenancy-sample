package com.abidat.user.service;

import com.abidat.user.dto.UserDto;
import com.abidat.user.entity.UserEntity;
import com.abidat.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by a.kuci on 4/27/2017.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userDto.getFullName());
        userEntity.setUserName(userDto.getUserName());
        userEntity.setAge(userDto.getAge());
        userRepository.save(userEntity);
        userDto.setId(userEntity.getId());
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> users = new ArrayList<>();
        Iterator<UserEntity> foundUsers = userRepository.findAll().iterator();
        while (foundUsers.hasNext()) {
            UserEntity userEntity = foundUsers.next();
            UserDto userDto = new UserDto();
            userDto.setId(userEntity.getId());
            userDto.setAge(userEntity.getAge());
            userDto.setFullName(userEntity.getFullName());
            userDto.setUserName(userEntity.getUserName());
            users.add(userDto);
        }
        return users;
    }


}
