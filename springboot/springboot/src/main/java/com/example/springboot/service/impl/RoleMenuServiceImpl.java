package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.RoleMenu;
import com.example.springboot.mapper.RoleMenuMapper;
import com.example.springboot.service.IRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
	
	@Resource
	private RoleMenuMapper roleMenuMapper;
	
	@Override
	public List<Integer> selectByRoleId(Integer roleId) {
		return roleMenuMapper.selectByRoleId(roleId);
	}
}
