package com.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.utils.ResBody;
import com.utils.enums.LzType;
import com.web.entity.Bgr;
import com.web.entity.Lzxx;
import com.web.service.BgrService;
import com.web.service.LzxxService;

/**
 * 流转相关的API
 * @author 夏夜梦星辰
 * 
 */

@Controller
@RequestMapping("/lz")
public class LzController {
	private static Logger log = Logger.getLogger(LzController.class);
	@Autowired
	private LzxxService lzxxService;
	@Autowired
	private BgrService bgrService;
	@Autowired
	private ServletContext context;
	
	/**
	 * 保存流转信息
	 * @param selectedIds 加入到清单的资产ID(JSON格式)
	 * @param operate 操作类型(1.出库 2.流转 3.回收)
	 * @return
	 */
	@PostMapping("/save")
	@ResponseBody
	public Object save(String selectedIds, LzType operate, String bgrId) {
		JSONArray arr = (JSONArray) JSON.parse(selectedIds);
		Set<String> zcIds = new HashSet<String>(); //用set集合自动去重
		for(Object selectedId : arr) {
			if(selectedId != null) {
				zcIds.add(selectedId.toString());
			}
		}
		if(zcIds.isEmpty()) {
			log.warn("未接收到选中的资产信息");
			return new ResBody(0, "未接收到选中的资产信息");
		}
		String operateId = lzxxService.save(zcIds, operate, bgrId);
		if(operateId == null) {
			return new ResBody(0, "保存流转信息失败");
		}
		return operateId;
	}
	/**
	 * 输出二维码图片
	 * @param operateId 操作ID
	 * @param response HTTP响应
	 */
	@RequestMapping("/outputQrcode/{operateId}")
	public void outputQrcode(@PathVariable("operateId")String operateId, HttpServletResponse response) {
		try {
			lzxxService.outputQrcode(operateId, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 拍照上传 (用流转ID和资产ID可以唯一确定到流转表当中的一条数据)
	 * @param file
	 * @param lzId 流转ID
	 * @param zcId 资产ID
	 */
	@PostMapping("/uploadPhoto")
	@ResponseBody
	public ResBody uploadPhoto(@RequestParam("uploadPhoto") MultipartFile photo, String operateId, String zcId) {
		String photoPath = lzxxService.writeFile(photo, context);
		if(photoPath == null) {
			return new ResBody(0, "文件上传失败");
		}
		//根据lzId找到operateId , 根据operateId和zcId确定流转表中的一条数据
		Lzxx lzxx = lzxxService.findByOperateIdAndZcId(operateId, zcId);
		if(lzxx == null) {
			return new ResBody(0, "未获得对应流转信息");
		}
		lzxxService.updatePhotoId(lzxx, photoPath, context);
		ResBody res = new ResBody(1, "文件上传成功");
		res.setData(photoPath);
		return res;
	}
	/**
	 * 获取图片文件(字节流输出)
	 * @param photoPath 图片路径
	 * @param response http响应对象
	 */
	@GetMapping("/readPhoto")
	public void readPhoto(String photoPath, HttpServletResponse response) {
		try {
			lzxxService.readFile(photoPath, response.getOutputStream(), context);
		} catch (IOException e) {
			log.error("读取文件错误!", e);
		}
	}
	/**
	 * 验证接收方的操作是否完成
	 * @param operateId
	 * @return 
	 */
	@GetMapping("/checkFinished")
	@ResponseBody
	public ResBody checkFinished(String operateId) {
		if(lzxxService.checkFinished(operateId)) {
			return new ResBody(0, "finished");//已完成
		} else {
			return new ResBody(1, "unfinish"); //未完成
		}
	}
	/**
	 * 接收方确认交接
	 * @param operateId 操作ID
	 * @return
	 */
	@GetMapping("/finished")
	@ResponseBody
	public ResBody finished(String operateId, LzType operate, String bgrId) {
		if(lzxxService.finished(operateId, operate, bgrId) > 0) {
			return new ResBody(1, "操作成功");
		} else {
			return new ResBody(0, "操作失败");
		}
	}
	
	@GetMapping("/count/{operateId}")
	@ResponseBody
	public ResBody count(@PathVariable("operateId")String operateId) {
		/*
            <th>出库记录编号</th>
            <th>资产种类数量</th>
            <th>领用人</th>
            <th>备注</th>
            <th>日期</th>
            <th>照片数量</th>
		 */
		Map<String, Object> countResult = new HashMap<String, Object>();
		ResBody res = new ResBody(1, "统计成功");
		res.setData(countResult);
		
		countResult.put("typeCount", lzxxService.typeCount(operateId));
		countResult.put("photoNum", lzxxService.countPhotoNum(operateId));//照片数量
		List<Object[]> result = lzxxService.getLzxxDetail(operateId);
		if(!result.isEmpty()) {
			countResult.put("lyr", result.get(0)[0]);
			if(result.get(0)[1] instanceof Date) {
				Date date = (Date) result.get(0)[1];
				countResult.put("rq", new SimpleDateFormat("yyyy-MM-dd").format(date));
			}
		}
		return res;
	}
	/**
	 * 检查某次流转是否已经全部上传照片
	 * @param operateId 操作ID
	 * @return res.status是1表示已经全部上传, 是0表示未全部上传
	 */
	@GetMapping("/checkUpload")
	@ResponseBody
	public ResBody checkUpload(String operateId) {
		return new ResBody(lzxxService.checkUpload(operateId), null);
	}
	
	/**
	 * 对方无法扫码 - 保存对方ID到流转信息
	 * @param operateId 操作ID
	 * @param targetName 对方姓名
	 * @return
	 */
	@PostMapping("/saveTargetTel")
	@ResponseBody
	public ResBody saveTargetTel(String operateId, LzType operate, String targetTel) {
		List<Bgr> targetBgr = bgrService.findByLxdh(targetTel);
		if(targetBgr.isEmpty()) {
			return new ResBody(0, "未找到对应的用户");
		}
		return this.finished(operateId, operate, targetBgr.get(0).getUuid());
	}
}
