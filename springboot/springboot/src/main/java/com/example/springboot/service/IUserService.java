package com.example.springboot.service;

import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.controller.dto.UserPasswordDTO;
import com.example.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    void register(User user);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
