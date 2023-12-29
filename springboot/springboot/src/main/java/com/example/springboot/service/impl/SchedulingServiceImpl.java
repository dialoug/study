package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Scheduling;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.SchedulingMapper;
import com.example.springboot.service.ISchedulingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SchedulingServiceImpl extends ServiceImpl<SchedulingMapper, Scheduling> implements ISchedulingService {
	
	@Resource
	private SchedulingMapper schedulingMapper;
	
	@Override
	public List<User> userByDate(String date, String department) {
		return schedulingMapper.userByDate(date,department);
	}
}
