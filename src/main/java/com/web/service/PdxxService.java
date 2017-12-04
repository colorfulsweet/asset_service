package com.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dao.PdxxRepository;
import com.web.dao.PhotoRepository;
import com.web.dao.ZichanRepository;
import com.web.entity.Pdxx;
import com.web.entity.PhotoIndex;
import com.web.entity.Zichan;

@Service
public class PdxxService {
	
	@Autowired
	private PdxxRepository pdxxRep;
	
	@Autowired
	private PhotoRepository photoRep;
	
	@Autowired
	private ZichanRepository zichanRep;
	/**
	 * 保存照片索引数据
	 * @param photoPath 照片路径
	 * @param zcUuid 资产uuid
	 * @param fileName 上传的文件原本的名称
	 * @return 照片索引数据ID
	 */
	public String savePhotoIndex(String photoPath, String zcUuid, String fileName) {
		PhotoIndex photoIndex = new PhotoIndex();
		//TODO 读取照片详细信息
		photoIndex.setFkAssetId(zcUuid);
		photoIndex.setPhotoPath(photoPath);
		//照片名称
		photoIndex.setPhotoName(fileName);
		//照片格式
		photoIndex.setPhotoFormat(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()));
		return photoRep.save(photoIndex).getId();
	}
	
	/**
	 * 保存盘点信息
	 * @param pdxx
	 * @param status 资产状态(正常 损坏 丢失 其他)
	 * @param photoPath 照片路径
	 */
	public void savePdxx(Pdxx pdxx, String status, String photoPath, String pdzt) {
		//写入盘点信息
		PhotoIndex photoIndex = photoRep.findByPhotoPath(photoPath);
		Zichan zc = zichanRep.findOne(pdxx.getFkZichanUuid());
		pdxx.setFkBgrBgrid(zc.getBgr().getUuid()); //保管人ID
		pdxx.setPdsj(new Date()); //盘点时间
		pdxx.setShul(zc.getShul()); //数量
		pdxx.setUnit(zc.getDanwei()); //单位
		pdxx.setFkZhaopianId(photoIndex.getId());
		pdxxRep.save(pdxx);

		zc.setPdzt(pdzt);
		zc.setStatus(status);
		zichanRep.save(zc);
	}
	
	/**
	 * 保存入库信息(相当于入库之后的首次盘点)
	 * @param pdxx
	 * @param photoPath 照片路径
	 */
	public void saveRk(Pdxx pdxx, String photoPath) {
		//写入盘点信息
		PhotoIndex photoIndex = photoRep.findByPhotoPath(photoPath);
		Zichan zc = zichanRep.findOne(pdxx.getFkZichanUuid());
		pdxx.setFkBgrBgrid(zc.getBgr().getUuid()); //保管人ID
		pdxx.setPdsj(new Date()); //盘点时间
		pdxx.setShul(zc.getShul()); //数量
		pdxx.setUnit(zc.getDanwei()); //单位
		pdxx.setFkZhaopianId(photoIndex.getId());
		pdxxRep.save(pdxx);

		zc.setPdzt("未盘点");
		zichanRep.save(zc);
	}
}
