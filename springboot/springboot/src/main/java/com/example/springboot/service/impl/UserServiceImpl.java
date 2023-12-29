package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Constants;
import com.example.springboot.common.RoleEnum;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.controller.dto.UserPasswordDTO;
import com.example.springboot.entity.Menu;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IMenuService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
	private static final Log LOG = Log.get();
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private IMenuService menuService;
	
	@Override
	public UserDTO login(UserDTO userDTO) {
		User one = getUserInfo(userDTO);
		if (one != null) {
			BeanUtil.copyProperties(one, userDTO, true);
			// 设置token
			String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
			userDTO.setToken(token);
			
			String role = one.getRole(); // ROLE_ADMIN
			// 设置用户的菜单列表
			List<Menu> menus = menuService.listByRole(role);
			userDTO.setMenus(menus);
			return userDTO;
		} else {
			throw new ServiceException(Constants.CODE_400, "用户名或密码错误");
		}
	}
	
	@Override
	public void register(User user) {
		User one = getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
		if (one == null) {
			user.setRole("3");
//			BeanUtil.copyProperties(userDTO, one, true);
			// 默认一个普通用户的角色
			save(user);  // 把 copy完之后的用户对象存储到数据库
		} else {
			throw new ServiceException(Constants.CODE_600, "用户已存在");
		}
	}
	
	@Override
	public void updatePassword(UserPasswordDTO userPasswordDTO) {
		int update = userMapper.updatePassword(userPasswordDTO);
		if (update < 1) {
			throw new ServiceException(Constants.CODE_600, "密码错误");
		}
	}
	
	private User getUserInfo(UserDTO userDTO) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", userDTO.getUsername());
		queryWrapper.eq("password", userDTO.getPassword());
		User one;
		try {
			one = getOne(queryWrapper); // 从数据库查询用户信息
		} catch (Exception e) {
			LOG.error(e);
			throw new ServiceException(Constants.CODE_500, "系统错误");
		}
		return one;
	}
	
}
