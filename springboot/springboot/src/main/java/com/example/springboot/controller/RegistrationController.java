package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Registration;
import com.example.springboot.service.IRegistrationService;
import com.example.springboot.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/registration")
public class RegistrationController {
	
	@Resource
	private IRegistrationService registerService;
	@Resource
	private IUserService userService;
	
	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public Result edit(@RequestBody Registration registration) {
		if (registration.getId() != null) {
			LocalDateTime dateTime = LocalDateTime.now();
			registration.setEndTime(dateTime.toLocalDate() + " " + dateTime.toLocalTime().toString().split("\\.")[0]);
		}
		registerService.updateById(registration);
		return Result.success();
	}
	
	/**
	 * 新增
	 */
	@PostMapping("/save")
	public Result save(@RequestBody Registration registration) {
		registerService.save(registration);
		return Result.success();
	}
	
	/**
	 * 根据id删除
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Integer id) {
		registerService.removeById(id);
		return Result.success();
	}
	
	/**
	 * 批量删除
	 */
	@PostMapping("/del/batch")
	public Result deleteBatch(@RequestBody List<Integer> ids) {
		registerService.removeByIds(ids);
		return Result.success();
	}
	
	/**
	 * 查询所有
	 */
	@GetMapping
	public Result findAll() {
		return Result.success(registerService.list());
	}
	
	/**
	 * 根据id查询
	 */
	@GetMapping("/{id}")
	public Result findOne(@PathVariable Integer id) {
		return Result.success(registerService.getById(id));
	}
	
	/**
	 * 分页查询
	 */
	@GetMapping("/page")
	public Result findPage(@RequestParam(defaultValue = "") String name,
	                       @RequestParam Integer pageNum,
	                       @RequestParam Integer pageSize,
	                       @RequestParam(defaultValue = "") String patientId,
	                       @RequestParam(defaultValue = "") String doctorId) {
		QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
		if (!"".equals(name)) {
			queryWrapper.like("name", name);
		}
		if (!"".equals(patientId)) {
			queryWrapper.eq("patient_id", patientId);
		}
		if (!"".equals(doctorId)) {
			queryWrapper.eq("doctor_id", doctorId);
		}
		updateState();
		return Result.success(registerService.page(new Page<>(pageNum, pageSize), queryWrapper));
	}
	
	/**
	 * 根据医生id获取今天挂号
	 */
	@GetMapping("/today")
	public Result today(@RequestParam(defaultValue = "") String doctorId) {
		List<Registration> registrations = registerService.today(doctorId, getTodayDate());
		for (Registration registration : registrations) {
			registration.setPatient(userService.getById(registration.getPatientId()));
		}
		return Result.success(registrations);
	}
	
	/**
	 * 更新过期挂号状态
	 */
	private void updateState() {
		registerService.updateState(getTodayDate());
	}
	
	private String getTodayDate() {
		LocalDate currentDate = LocalDate.now();
		String formattedDate = currentDate.toString();
		return formattedDate.substring(5, 10);
	}
}

