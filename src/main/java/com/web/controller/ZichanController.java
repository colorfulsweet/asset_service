package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.entity.Zichan;
import com.web.service.ZichanService;

@RestController
public class ZichanController {
	@Autowired
    private ZichanService zichanService;
	
	
	/**
	 * 查询
	 * @param zcID 资产编码
	 * @param mingch 名称
	 * @param lbie 类别
	 * @param uuids 多个UUID,以逗号分隔
	 * @return
	 */
	@GetMapping("/zichan/list")
	public List<Zichan> list(String zcID, String mingch, String lbie, String uuids) {
		return zichanService.find(zcID, mingch, lbie, uuids);
	}
	/**
	 * 根据保管人ID查询资产信息
	 * @param bgrId
	 * @return
	 */
	@GetMapping("/zichan/findByBgrId")
	public List<Zichan> findByBgrId(String bgrId) {
		return zichanService.findByBgrId(bgrId);
	}
	
	/**
	 * 根据流转ID查询资产数据
	 * @param lzIds
	 * @return
	 */
	@PostMapping("/zichan/getByOperateId")
	public List<Zichan> getByOperateId(String operateId) {
		return zichanService.getByOperateId(operateId);
	}
}
