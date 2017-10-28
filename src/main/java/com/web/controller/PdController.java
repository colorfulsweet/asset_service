package com.web.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.utils.FileUtil;
import com.utils.ResBody;
import com.web.entity.Pdxx;
import com.web.service.PdxxService;

/**
 * 盘点相关的API
 * @author 夏夜梦星辰
 *
 */
@RestController
@RequestMapping("/pd")
public class PdController {
	@Autowired
	private PdxxService pdxxService;
	
	@Autowired
	private FileUtil fileUtil;
	@Autowired
	private ServletContext context;
	/**
	 * 拍照上传
	 * @param file
	 * @param zcUuid 资产表uuid
	 */
	@PostMapping("/uploadPhoto")
	public ResBody uploadPhoto(@RequestParam("uploadPhoto") MultipartFile photo, String zcUuid) {
		String photoPath = fileUtil.writeFile(photo, context);
		if(photoPath == null) {
			return new ResBody(0, "文件上传失败");
		}
		//保存照片索引信息
		pdxxService.savePhotoIndex(photoPath, zcUuid, photo.getOriginalFilename());
		ResBody res = new ResBody(1, "文件上传成功");
		res.setData(photoPath);
		return res;
	}
	
	/**
	 * 保存盘点信息
	 * @param pdxx
	 * @param status 资产状态(正常 损坏 丢失 其他)
	 * @param photoPath 已上传照片的路径
	 * @return
	 */
	@PostMapping("/save")
	public ResBody save(Pdxx pdxx, String status, String photoPath) {
		pdxxService.savePdxx(pdxx, status, photoPath);
		return new ResBody(1, "保存成功");
	}
}
