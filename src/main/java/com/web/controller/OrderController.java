package com.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	/**
	 * 保存订单信息
	 * @param selectedIds 加入到清单的资产ID(JSON格式)
	 * @param operate 操作类型(1.出库 ...)
	 * @return
	 */
	@PostMapping("/save")
	public String save(String selectedIds, String operate) {
		JSONArray arr = (JSONArray) JSON.parse(selectedIds);
		List<String> zcIds = new ArrayList<String>();
		for(Object selectedId : arr) {
			if(selectedId != null) {
				zcIds.add(selectedId.toString());
			}
		}
		//TODO 保存订单信息
		return null;
	}
}
