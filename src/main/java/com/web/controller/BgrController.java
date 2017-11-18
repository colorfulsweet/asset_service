package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utils.ResBody;
import com.web.entity.Bgr;
import com.web.service.BgrService;

@RestController
@RequestMapping("/bgr")
public class BgrController {
	@Autowired
	private BgrService bgrService;
	
	/**
	 * 保存保管人信息
	 * @param bgr
	 * @return
	 */
	@PostMapping("/save")
	public ResBody save(Bgr bgr) {
		bgrService.save(bgr);
		return new ResBody(1, "保存成功");
	}
}
