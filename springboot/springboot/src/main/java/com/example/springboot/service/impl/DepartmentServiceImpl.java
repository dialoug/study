package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Department;
import com.example.springboot.mapper.DepartmentMapper;
import com.example.springboot.service.IDepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
	
	@Override
	public List<Department> findList() {
		List<Department> list = this.list();
		List<Department> collect = list.stream().filter(e -> e.getPid() == 0).collect(Collectors.toList());
		for (Department department : collect) {
			department.setChildren(list.stream().filter(e -> e.getPid().equals(department.getId())).collect(Collectors.toList()));
		}
		return collect;
	}
}
