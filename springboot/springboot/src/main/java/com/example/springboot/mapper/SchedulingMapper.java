package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Scheduling;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SchedulingMapper extends BaseMapper<Scheduling> {
	
	List<User> userByDate(@Param("date") String date, @Param("department") String department);
}
