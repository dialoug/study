package com.example.springboot.mapper;

import com.example.springboot.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface MenuMapper extends BaseMapper<Menu> {
	
	List<Menu> listByRole(String role);
}
