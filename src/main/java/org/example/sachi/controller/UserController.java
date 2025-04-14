package org.example.sachi.controller;

import org.example.sachi.dto.UserDTO;
import org.example.sachi.service.UserService;
import org.example.sachi.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("save")
    private ResponseUtil createUser(@RequestBody UserDTO userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.createUser(userDto);
        return new ResponseUtil(200 ,"User Create" , null);

    }

    @GetMapping("getAll")
    private ResponseUtil loadUser(){
        return new ResponseUtil(201, "load user" ,userService.loadUser());
    }
}