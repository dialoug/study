package com.example.springboot.controller;

import javafx.geometry.HorizontalDirection;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class Demo {
	public static void main(String[] args) {
		
		System.out.println(LocalDateTime.now().toLocalTime().toString().split("\\.")[0]);
	}
	
	
}
