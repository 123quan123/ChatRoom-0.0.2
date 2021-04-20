package com.me.section;

import com.me.util.CreateFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PictureReceive {
	private final static String DEFAULT_PATH = "F:\\ChatRoom的图片文件夹\\";
	private String path;
	
	public PictureReceive(){
	}
	public String getPath() {
		return path;
	}
	
	public File setPath(String path) {
		this.path = DEFAULT_PATH + path + ".bmp";
		File file = CreateFileUtil.createFile(this.path);
		System.out.println(file);
		return file;
	}


	
	
}
