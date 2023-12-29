package com.example.springboot.controller;


import cn.hutool.core.io.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@RequestMapping("/upload")
	public Object upload(MultipartFile file) {
		String url = "";
		try {
			String path = System.getProperty("user.dir") + "\\src\\main\\resources\\file\\";
			String originalFilename = file.getOriginalFilename();
			String type = originalFilename.substring(originalFilename.lastIndexOf("."));
			// 定义一个文件唯一的标识码
			String fileUUID = new Date().getTime() + type;
			
			File uploadFile = new File(path + fileUUID);
			// 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
			File parentFile = uploadFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			
			// 上传文件到磁盘
			file.transferTo(uploadFile);
			// 数据库若不存在重复文件，则不删除刚才上传的文件
			url = "http://localhost/file/" + fileUUID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	@GetMapping("/{uuid}")
	public void down(@PathVariable String uuid, HttpServletResponse response) {
		// 根据文件的唯一标识码获取文件
		try {
			String path = System.getProperty("user.dir") + "\\src\\main\\resources\\file\\";
			
			File uploadFile = new File(path + uuid);
			// 设置输出流的格式
			ServletOutputStream os = response.getOutputStream();
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uuid, "UTF-8"));
			response.setContentType("application/octet-stream");
			
			// 读取文件的字节流
			try {
				os.write(FileUtil.readBytes(uploadFile));
			} catch (Exception e) {
				System.err.println("文件下载失败，文件不存在");
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
