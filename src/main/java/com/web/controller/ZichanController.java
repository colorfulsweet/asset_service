package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * @return
	 */
	@GetMapping("/zichan/list")
	public List<Zichan> list(String zcID, String mingch, String lbie) {
		return zichanService.find(zcID, mingch, lbie);
	}
}