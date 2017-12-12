package com.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.zxing.WriterException;
import com.utils.FileUtil;
import com.utils.QrcodeUtils;
import com.web.entity.AppVersion;
import com.web.service.AppVersionService;

/**
 * app实现热更新相关的方法
 * 包含更新包的上传与下载, 获取当前版本, 
 * @author 夏夜梦星辰
 *
 */

@Controller
@RequestMapping("/appUpdate")
public class AppUpdateController {
	private static Logger log = Logger.getLogger(AppUpdateController.class);
	
	@Autowired
	private AppVersionService appVersionService;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private QrcodeUtils qrcodeUtils;
	
	/**
	 * 根据当前的版本号获取下一个增量升级包
	 * @param currentVersion 当前的版本号
	 * @return 下一个增量升级的版本号, 没有则返回null
	 */
	@GetMapping("/getNextVersion")
	public String getNextVersion(String currentVersion) {
		
		return null;
	}
	
	/**
	 * 下载最新的完整安装包
	 * @param response
	 * @param request
	 * @param os 平台类型(iOS或者Android)
	 * @throws IOException
	 */
	@GetMapping("/getLatestVersion")
	public void getLatestVersion(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(name="os",defaultValue="Android")String os) throws IOException {
		
		AppVersion appVersion = appVersionService.getLatestVersionUrl(os);
		if(appVersion == null) {
			response.getWriter().write("当前没有版本可供下载");
			return;
		}
		this.download(appVersion, response, request);
	}
	
	/**
	 * 根据ID下载安装包
	 * @param response
	 * @param request
	 * @param uuid
	 * @throws IOException 
	 */
	@GetMapping("/downloadById")
	public void downloadById(HttpServletResponse response, HttpServletRequest request, 
			String uuid) throws IOException {
		AppVersion appVersion = appVersionService.getById(uuid);
		if(appVersion == null) {
			response.getWriter().write("当前没有版本可供下载");
			return;
		}
		this.download(appVersion, response, request);
	}
	
	/**
	 * 根据内容文本生成二维码
	 * @param response 
	 * @param content 二维码包含的文本
	 */
	@GetMapping("/outputDownloadQrcode")
	public void outputDownloadQrcode (HttpServletResponse response, String content) {
		try {
			qrcodeUtils.createQrcode(content, response.getOutputStream());
		} catch (WriterException | IOException e) {
			log.error("二维码输出错误!", e);
		}
	}
	
	private void download(AppVersion appVersion, HttpServletResponse response, 
			HttpServletRequest request) throws IOException {
		String downloadFileName = fileUtil.encodeDownloadName(request, 
				"资产管理v" + appVersion.getVersion() + "." + appVersion.getExt());
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition","attachment; filename="+downloadFileName);
        fileUtil.readUploadFile(appVersion.getFilePath(), response.getOutputStream());
	}
}
