package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.RoleMenu;

import java.util.List;

public interface IRoleMenuService extends IService<RoleMenu> {
	List<Integer> selectByRoleId(Integer roleId);
}
