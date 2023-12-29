package com.example.springboot.service;

import com.example.springboot.entity.Scheduling;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.User;

import java.util.List;


public interface ISchedulingService extends IService<Scheduling> {
	
	List<User> userByDate(String date,String department);
}
