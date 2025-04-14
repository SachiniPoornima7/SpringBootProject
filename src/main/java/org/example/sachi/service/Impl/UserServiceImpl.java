package org.example.sachi.service.Impl;

import org.example.sachi.dto.UserDTO;
import org.example.sachi.entity.User;
import org.example.sachi.repo.UserRepo;
import org.example.sachi.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public void createUser(UserDTO userDto) {

        if (userRepo.existsById(userDto.getUserId())){
            throw new RuntimeException("user allready exit");
        }else {
            userRepo.save(modelMapper.map(userDto, User.class));
        }

    }

    @Override
    public List<UserDTO> loadUser() {

        return modelMapper.map(userRepo.findAll(),new TypeToken<List<UserDTO>>(){}.getType());
    }
}
