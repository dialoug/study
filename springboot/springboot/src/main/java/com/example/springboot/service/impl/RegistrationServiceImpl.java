package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Registration;
import com.example.springboot.mapper.RegistrationMapper;
import com.example.springboot.service.IRegistrationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements IRegistrationService {
	
	@Resource
	private RegistrationMapper registrationMapper;
	
	@Override
	public void updateState(String date) {
		String[] ids = registrationMapper.selectListId(date);
		List<Registration> registrationList = new ArrayList<Registration>();
		for (String id : ids) {
			Registration registration = new Registration();
			registration.setId(id);
			registration.setState("2");
			registrationList.add(registration);
		}
		if (registrationList.size() > 0) {
			this.updateBatchById(registrationList);
		}
	}
	
	@Override
	public List<Registration> today(String doctorId, String todayDate) {
		return registrationMapper.today(doctorId, todayDate);
	}
}
