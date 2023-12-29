package com.example.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.config.interceptor.AuthAccess;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.controller.dto.UserPasswordDTO;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private IUserService userService;
	
	@PostMapping("/login")
	public Result login(@RequestBody UserDTO userDTO) {
		String username = userDTO.getUsername();
		String password = userDTO.getPassword();
		if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
			return Result.error(Constants.CODE_400, "参数错误");
		}
		UserDTO dto = userService.login(userDTO);
		return Result.success(dto);
	}
	
	@PostMapping("/register")
	public Result register(@RequestBody User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
			return Result.error(Constants.CODE_400, "用户名或者密码不能为空");
		}
		user.setName(user.getUsername());
		userService.register(user);
		return Result.success();
	}
	
	// 新增或者更新
	@PostMapping
	public Result save(@RequestBody User user) {
		String username = user.getUsername();
		if (StrUtil.isBlank(username)) {
			return Result.error(Constants.CODE_400, "参数错误");
		}
		if (StrUtil.isBlank(user.getName())) {
			user.setName(username);
		}
		if (user.getId() != null) {
			user.setPassword(null);
		} else {
			List<User> list = userService.list(new QueryWrapper<User>().eq("username", username));
			
			if (list.size() > 0) {
				throw new ServiceException(Constants.CODE_600, "用户名已存在");
			}
			if (user.getPassword() == null) {
				user.setPassword("123");
			}
		}
		return Result.success(userService.saveOrUpdate(user));
	}
	
	/**
	 * 修改密码
	 */
	@PostMapping("/password")
	public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
		userService.updatePassword(userPasswordDTO);
		return Result.success();
	}
	
	@AuthAccess
	@PutMapping("/reset")
	public Result reset(@RequestBody UserPasswordDTO userPasswordDTO) {
		if (StrUtil.isBlank(userPasswordDTO.getUsername()) || StrUtil.isBlank(userPasswordDTO.getPhone())) {
			throw new ServiceException("-1", "参数异常");
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", userPasswordDTO.getUsername());
		queryWrapper.eq("phone", userPasswordDTO.getPhone());
		List<User> list = userService.list(queryWrapper);
		if (CollUtil.isNotEmpty(list)) {
			User user = list.get(0);
			user.setPassword("123");
			userService.updateById(user);
		}
		return Result.success();
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Integer id) {
		return Result.success(userService.removeById(id));
	}
	
	@PostMapping("/del/batch")
	public Result deleteBatch(@RequestBody List<Integer> ids) {
		return Result.success(userService.removeByIds(ids));
	}
	
	@GetMapping
	public Result findAll() {
		return Result.success(userService.list());
	}
	
	@GetMapping("/{id}")
	public Result findOne(@PathVariable Integer id) {
		return Result.success(userService.getById(id));
	}
	
	@GetMapping("/username/{username}")
	public Result findByUsername(@PathVariable String username) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		return Result.success(userService.getOne(queryWrapper));
	}
	
	/**
	 * 患者分页查询
	 */
	@GetMapping("/patientPage")
	public Result patientPagePage(@RequestParam Integer pageNum,
	                              @RequestParam Integer pageSize,
	                              @RequestParam(defaultValue = "") String name) {
		
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("id");
		if (!"".equals(name)) {
			queryWrapper.like("name", name);
		}
		queryWrapper.eq("role", 3);
		return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
	}
	
	/**
	 * 医生分页查询
	 */
	@GetMapping("/doctorPage")
	public Result doctorPage(@RequestParam Integer pageNum,
	                         @RequestParam Integer pageSize,
	                         @RequestParam(defaultValue = "") String departmentId,
	                         @RequestParam(defaultValue = "") String name) {
		
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("id");
		if (!"".equals(name)) {
			queryWrapper.like("name", name);
		}
		if (!"".equals(departmentId)) {
			queryWrapper.eq("department_id", departmentId);
		}
		queryWrapper.eq("role", 2);
		return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
	}
	
	/**
	 * 根据科室获取医生列表
	 */
	@GetMapping("/listByDepartment/{departmentId}")
	public Result listByDepartment(@PathVariable Integer departmentId) {
		return Result.success(userService.list(new QueryWrapper<User>().eq("department_id", departmentId).eq("role", 2)));
	}
}

