package com.example.springboot.service;

import com.example.springboot.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IDepartmentService extends IService<Department> {
	
	List<Department> findList();
}
