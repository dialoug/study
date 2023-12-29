package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import org.springframework.web.multipart.MultipartFile;
import com.example.springboot.entity.User;
import com.example.springboot.utils.TokenUtils;

import com.example.springboot.service.IChecksService;
import com.example.springboot.entity.Checks;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/checks")
public class ChecksController {

    @Resource
    private IChecksService checksService;

	/**
	* 新增或编辑
	*/
	@PostMapping
    public Result save(@RequestBody Checks checks) {
        checksService.saveOrUpdate(checks);
        return Result.success();
    }

	/**
	* 根据id删除
	*/
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        checksService.removeById(id);
        return Result.success();
    }

	/**
	* 批量删除
	*/
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        checksService.removeByIds(ids);
        return Result.success();
    }

	/**
	* 查询所有
	*/
    @GetMapping
    public Result findAll() {
        return Result.success(checksService.list());
    }

	/**
	* 根据id查询
	*/
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(checksService.getById(id));
    }

	/**
	* 分页查询
	*/
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Checks> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(checksService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

