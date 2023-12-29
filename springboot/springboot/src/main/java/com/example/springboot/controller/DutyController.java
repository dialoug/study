package com.example.springboot.controller;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/duty")
public class DutyController {
	
	@GetMapping("/getDutyDate")
	public Result getDutyDate(@RequestParam Integer day) {
		LocalDate currentDate = LocalDate.now(); // 获取当前日期
		
		List<String> dateList = new ArrayList<>();
		for (int i = 0; i < day; i++) {
			String formattedDate = currentDate.plusDays(i).toString();
			// 提取月和日部分，格式为 MM-dd
			String monthAndDay = formattedDate.substring(5, 10);
			dateList.add(monthAndDay);
		}
		return Result.success(dateList);
	}
}
