package com.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.utils.LzFlag;
import com.utils.ResBody;
import com.web.service.LzxxService;

/**
 * 流转相关的API
 * @author Sookie
 * 
 * 
 */

@Controller
@RequestMapping("/lz")
public class LzController {
	private static Logger log = Logger.getLogger(LzController.class);
	@Autowired
	private LzxxService lzxxService;
	@Autowired
	private ServletContext context;
	
	/**
	 * 保存流转信息
	 * @param selectedIds 加入到清单的资产ID(JSON格式)
	 * @param operate 操作类型(1.出库 2.流转 3.回收)
	 * @return
	 */
	@PostMapping("/save")
	@ResponseBody
	public Object save(String selectedIds, String operate) {
		JSONArray arr = (JSONArray) JSON.parse(selectedIds);
		Set<String> zcIds = new HashSet<String>(); //用set集合自动去重
		for(Object selectedId : arr) {
			if(selectedId != null) {
				zcIds.add(selectedId.toString());
			}
		}
		if(zcIds.isEmpty()) {
			log.warn("未接收到选中的资产信息");
			return new ResBody(0, "未接收到选中的资产信息");
		}
		LzFlag flag = null;
		switch(operate) {
			case "1" : flag = LzFlag.CK;break; //出库
			case "2" : flag = LzFlag.LZ;break; //流转
			case "3" : flag = LzFlag.HS;break; //回收
			default : log.warn("未知的操作类型标识 : " + operate);
		}
		return lzxxService.save(zcIds, flag);
	}
	/**
	 * 输出二维码图片
	 * @param operateId 操作ID
	 * @param response HTTP响应
	 */
	@RequestMapping("/outputQrcode/{operateId}")
	public void outputQrcode(@PathVariable("operateId")String operateId, HttpServletResponse response) {
		try {
			lzxxService.outputQrcode(operateId, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 拍照上传 (用流转ID和资产ID可以唯一确定到流转表当中的一条数据)
	 * @param file
	 * @param lzId 流转ID
	 * @param zcId 资产ID
	 */
	@PostMapping("/uploadPhoto")
	@ResponseBody
	public void uploadPhoto(@RequestParam("uploadPhoto") MultipartFile photo, String lzId, String zcId) {
		byte[] buf = new byte[1024];
		File outputPath = new File(context.getRealPath("/upload"));
		if(!outputPath.exists()) {
			outputPath.mkdirs(); //如果目录不存在则直接创建
		}
		String fileName = photo.getOriginalFilename(); //选择的文件原本的名字
		
		String fileUUID = UUID.randomUUID().toString();
		String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		
		log.info("fileUUID : " + fileUUID);
		try{
			try(InputStream input = photo.getInputStream(); 
					OutputStream output = new FileOutputStream(outputPath.getAbsolutePath() + "/" + fileUUID + ext)){
				int len = input.read(buf);
				while(len > 0) {
					output.write(buf, 0, len);
					len = input.read(buf);
				}
			}
		} catch (IOException e) {
			log.error("文件上传错误!", e);
		}
		
		//TODO 根据lzId找到operateId , 根据operateId和zcId确定流转表中的一条数据
	}
}
