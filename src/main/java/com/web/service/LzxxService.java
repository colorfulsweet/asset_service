package com.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utils.LzFlag;
import com.web.dao.LzxxRespository;
import com.web.entity.Lzxx;

@Service
public class LzxxService {
	
	@Autowired
	private LzxxRespository lzxxResp;
	
	/**
	 * 保存流转信息
	 * @param zcIds 资产ID
	 * @param flag 标识(用于区分 出库 流转 回收)
	 * @return 多个流转的ID
	 */
	public List<String> save(Set<String> zcIds, LzFlag flag) {
		List<String> lzIds = new ArrayList<String>();
		for(String zcId : zcIds) {
			Lzxx lzxx = new Lzxx();
			lzxx.setBiaozhi(flag.getFlag().toString());
			lzxx.setFkZichanZcID(zcId);
			lzxx.setLzsj(new Date());
			
			//TODO 上传保存照片 记录照片ID?
			lzIds.add(lzxxResp.save(lzxx).getUUID());
		}
		return lzIds;
	}
}
