package com.abidat.user.controller;


import com.abidat.configuration.TenantTracker;
import com.abidat.user.dto.UserDto;
import com.abidat.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {


    @Autowired
    UserService userService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public UserDto createUser(@RequestBody UserDto userDto, @RequestHeader("TENANT-ID") String tenantId) {
        //TODO: Replace this in an interceptor?
        TenantTracker.setCurrentTenant(tenantId);
        return userService.createUser(userDto);
    }


    @ResponseStatus(value = HttpStatus.FOUND)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<UserDto> getAllUsers(@RequestHeader("TENANT-ID") String tenantId) {
        TenantTracker.setCurrentTenant(tenantId);
        return userService.getAllUsers();
    }

}
