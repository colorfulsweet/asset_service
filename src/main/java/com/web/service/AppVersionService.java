package com.web.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utils.FileUtil;
import com.utils.PageUtil;
import com.web.dao.AppVersionRespository;
import com.web.entity.AppVersion;

@Service
public class AppVersionService {
	private static Logger log = Logger.getLogger(AppVersionService.class);
	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private AppVersionRespository appVersionResp;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<AppVersion> find(PageUtil page) {
		StringBuilder where = new StringBuilder(" where 1=1 ");
		
		TypedQuery<AppVersion> query = entityManager.createQuery("from AppVersion bean"+where.toString(), AppVersion.class);
		if(page != null) {
			query.setFirstResult(page.getRowStart());
			query.setMaxResults(page.getPageSize());
			Query countQuery = entityManager.createQuery("select count(*) from AppVersion bean"+where.toString());
			
			page.setRowCount(countQuery.getSingleResult().toString());
		}
		return query.getResultList();
	}
	
	public AppVersion saveUploadApp(MultipartFile mtpFile, AppVersion appVersion) {
		String fileName = mtpFile.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		switch (ext) {
		case "apk" : //安卓端安装包
			appVersion.setOs("Android");
			appVersion.setIncrement(false);
			break;
		case "ipa" : //爱疯端安装包
			appVersion.setOs("iOS");
			appVersion.setIncrement(false);
			break;
		case "wgt" : //增量包
			appVersion.setIncrement(true);
			break;
		default : 
			log.warn("未知的安装包类型:" + fileName);
			return null;
		}
		appVersion.setFilePath(fileUtil.writeFile(mtpFile, false)); //文件保存路径
		appVersion.setFileName(fileName); //文件名称
		appVersion.setExt(ext); //扩展名
		appVersion.setCreateTime(new Date()); //发布日期
		
		return appVersionResp.save(appVersion);
	}
	
	/**
	 * 获取最新版本的文件相对路径
	 * @param os 平台类型(iOS或者Android)
	 * @return AppVersion对象
	 */
	public AppVersion getLatestVersionUrl(String os) {
//		return appVersionResp.getLatestVersion(os);
		return null;
	}
}
