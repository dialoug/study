package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.RoleMenu;
import com.example.springboot.mapper.RoleMapper;
import com.example.springboot.service.IMenuService;
import com.example.springboot.service.IRoleMenuService;
import com.example.springboot.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
	
	@Resource
	private IRoleMenuService roleMenuService;
	
	@Resource
	private IMenuService menuService;
	
	@Transactional
	@Override
	public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
		
		// 先删除当前角色id所有的绑定关系
		roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
		
		// 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
		for (Integer menuId : menuIds) {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenuService.save(roleMenu);
		}
	}
	
	@Override
	public List<Integer> getRoleMenu(Integer roleId) {
		
		return roleMenuService.selectByRoleId(roleId);
	}
	
}
