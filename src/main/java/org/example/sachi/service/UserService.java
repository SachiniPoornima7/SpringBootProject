package org.example.sachi.service;

import org.example.sachi.dto.UserDTO;

import java.util.List;

public interface UserService {

    void createUser(UserDTO userDto);

    List<UserDTO> loadUser();

}
