package com.example.springboot.service.impl;

import com.example.springboot.entity.Drug;
import com.example.springboot.mapper.DrugMapper;
import com.example.springboot.service.IDrugService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug> implements IDrugService {

}
