package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Registration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RegistrationMapper extends BaseMapper<Registration> {
	
	@Select("SELECT id FROM `t_registration` WHERE SUBSTRING_INDEX(create_time,' ',1) < #{date} AND state = '0'")
	String[] selectListId(String date);
	
	@Select("SELECT * FROM `t_registration` WHERE SUBSTRING_INDEX(create_time,' ',1) = #{todayDate} AND doctor_id = #{doctorId}")
	List<Registration> today(@Param("doctorId") String doctorId, @Param("todayDate") String todayDate);
}