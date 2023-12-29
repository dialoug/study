package com.example.springboot.service.impl;

import com.example.springboot.entity.Checks;
import com.example.springboot.mapper.ChecksMapper;
import com.example.springboot.service.IChecksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ChecksServiceImpl extends ServiceImpl<ChecksMapper, Checks> implements IChecksService {

}
