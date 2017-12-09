package com.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.utils.FileUtil;
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
	
	@Autowired
	private AppVersionService appVersionService;
	
	@Autowired
	private FileUtil fileUtil;
	
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
	 * @param os 平台类型(iOS或者Android)
	 * @return
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
		String downloadFileName = fileUtil.encodeDownloadName(request, 
				"资产管理v" + appVersion.getVersion() + "." + appVersion.getExt());
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition","attachment; filename="+downloadFileName);
        fileUtil.readUploadFile(appVersion.getFilePath(), response.getOutputStream());
	}
}
