package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.entity.Zichan;
import com.web.service.ZichanService;

/**
 * 资产数据操作相关API
 * @author 夏夜梦星辰
 *
 */
@RestController
@RequestMapping("/zichan")
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
	@GetMapping("/list")
	public List<Zichan> list(String zcID, String mingch, String lbie, String uuids) {
		return zichanService.find(zcID, mingch, lbie, uuids);
	}
	/**
	 * 根据保管人ID查询资产信息
	 * @param bgrId
	 * @return
	 */
	@GetMapping("/findByBgrId")
	public List<Zichan> findByBgrId(String bgrId) {
		return zichanService.findByBgrId(bgrId);
	}
	
	/**
	 * 根据流转ID查询资产数据
	 * @param lzIds
	 * @return
	 */
	@PostMapping("/getByOperateId")
	public List<Zichan> getByOperateId(String operateId) {
		return zichanService.getByOperateId(operateId);
	}
	
	/**
	 * 查询资产的最后一条(时间最晚)流转记录当中的照片路径
	 * @param zcid 资产编码(不是zichan表的uuid)
	 * @return 图片相对路径
	 */
	@PostMapping("/findLastPhoto")
	public List<String> findLastPhoto(String zcid) {
		return zichanService.findLastPhoto(zcid);
	}
}
