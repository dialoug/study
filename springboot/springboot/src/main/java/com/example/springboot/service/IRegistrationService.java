package com.example.springboot.service;

import com.example.springboot.entity.Registration;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IRegistrationService extends IService<Registration> {
	
	void updateState(String date);
	
	List<Registration> today(String doctorId, String todayDate);
}
