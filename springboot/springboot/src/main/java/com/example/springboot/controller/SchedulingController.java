package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Scheduling;
import com.example.springboot.service.ISchedulingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/scheduling")
public class SchedulingController {
	
	@Resource
	private ISchedulingService schedulingService;
	
	
	/**
	 * 新增或编辑
	 */
	@PostMapping
	public Result save(@RequestBody Scheduling scheduling) {
		schedulingService.saveOrUpdate(scheduling);
		return Result.success();
	}
	
	/**
	 * 根据id删除
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Integer id) {
		schedulingService.removeById(id);
		return Result.success();
	}
	
	@DeleteMapping("/remove")
	public Result remove(@RequestParam String date, @RequestParam String doctorId) {
		schedulingService.remove(new QueryWrapper<Scheduling>().eq("date", date).eq("doctor_id", doctorId));
		return Result.success();
	}
	
	/**
	 * 批量删除
	 */
	@PostMapping("/del/batch")
	public Result deleteBatch(@RequestBody List<Integer> ids) {
		schedulingService.removeByIds(ids);
		return Result.success();
	}
	
	/**
	 * 查询所有
	 */
	@GetMapping
	public Result findAll() {
		return Result.success(schedulingService.list());
	}
	
	/**
	 * 根据id查询
	 */
	@GetMapping("/{id}")
	public Result findOne(@PathVariable Integer id) {
		return Result.success(schedulingService.getById(id));
	}
	
	/**
	 * 根据时间查询
	 */
	@GetMapping("/listByDate")
	public Result listByDate(@RequestParam String date) {
		return Result.success(schedulingService.list(new QueryWrapper<Scheduling>().eq("date", date)));
	}
	
	/**
	 * 获取用户列表根据时间
	 */
	@GetMapping("/userByDate")
	public Result userByDate(@RequestParam String date, @RequestParam String department) {
		return Result.success(schedulingService.userByDate(date, department));
	}
	
}

