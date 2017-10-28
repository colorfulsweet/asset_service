package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {
	private static Logger log = Logger.getLogger(FileUtil.class);
	
	@Value("${asset.upload.basePath}")
	private String uploadBasePath;
	
	@Value("${asset.upload.rootpath}")
	private String uploadRootpath;
	
	@Value("${asset.upload.datedir}")
	private String uploadDatedir;
	/**
	 * 将上传的文件写入到服务器本地
	 * @param photo
	 * @param context
	 * @return 图片文件保存路径
	 */
	public String writeFile(MultipartFile photo, ServletContext context) {
		DateFormat formatter = new SimpleDateFormat(uploadDatedir);
		
		String datePath = formatter.format(new Date());
		File outputPath = new File(getAbsolutePath(uploadRootpath + datePath, context));
		if(!outputPath.exists()) {
			outputPath.mkdirs(); //如果目录不存在则直接创建
		}
		String fileName = photo.getOriginalFilename(); //选择的文件原本的名字
		
		String fileUUID = UUID.randomUUID().toString();
		String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		
		log.info("fileUUID : " + fileUUID);
		byte[] buf = new byte[1024];
		try{
			try(InputStream input = photo.getInputStream(); 
					OutputStream output = new FileOutputStream(outputPath.getAbsolutePath() + "/" + fileUUID + ext)){
				int len = input.read(buf);
				while(len > 0) {
					output.write(buf, 0, len);
					len = input.read(buf);
				}
			}
			return uploadRootpath + datePath + "/" + fileUUID + ext;
		} catch (IOException e) {
			log.error("文件上传错误!", e);
			return null;
		}
	}
	/**
	 * 读取文件从输出流进行输出
	 * @param filePath 文件**相对**路径
	 * @param output 字节输出流
	 * @throws IOException 
	 */
	public void readFile(String filePath, OutputStream output, ServletContext context) throws IOException {
		InputStream input = new FileInputStream(getAbsolutePath(filePath, context));
		byte[] buf = new byte[1024];
		int len = input.read(buf);
		while(len > 0) {
			output.write(buf, 0, len);
			len = input.read(buf);
		}
		output.flush();
		output.close();
		input.close();
	}
	
	/**
	 * 根据文件的相对路径获取绝对路径(用于保存和读取文件)
	 * @param relativePath 相对路径
	 * @param context servlet上下文对象
	 * @return 绝对路径(从磁盘根路径开始)
	 */
	public String getAbsolutePath(String relativePath, ServletContext context) {
		if(StringHelper.isEmpty(uploadBasePath)) {
			return context.getRealPath(relativePath);
		} else {
			return uploadBasePath + relativePath;
		}
	}
}
