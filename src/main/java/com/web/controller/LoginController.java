package com.web.controller;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utils.ResBody;
import com.web.entity.Bgr;
import com.web.service.BgrService;

/**
 * 登陆相关API
 * @author 夏夜梦星辰
 *
 */
@RestController
public class LoginController {
	
	@Autowired
	private BgrService bgrService;
	
	
	@PostMapping(value="/login")
	public ResBody login(String user, String password) {
		ResBody res = new ResBody();
		if(StringHelper.isEmpty(user) || StringHelper.isEmpty(password)) {
			res.setStatus(0);
			res.setMsg("用户名/密码不能为空");
			return res;
		}
		Bgr bgr = bgrService.checkUser(user, DigestUtils.sha1Hex(password));
		
		if(bgr == null) {
			res.setStatus(0);
			res.setMsg("用户名/密码错误");
		} else {
			res.setStatus(1);
			bgr.setPassword(null); //密码信息不回传
			res.setData(bgr);
			bgr.setRoles(bgrService.queryQxByBgr(bgr.getUuid()));
			bgr.setToken(DigestUtils.sha256Hex(bgr.getUser() + new Date().getTime()));
		}
		return res;
	}
	/**
	 * 修改用户的密码
	 * @param bgrId 用户ID
	 * @param oldPwd 原密码(先校验是否正确)
	 * @param newPwd 新密码
	 * @return 
	 */
	@PostMapping(value="/changePwd")
	public ResBody changePwd(String bgrId, String oldPwd, String newPwd) {
		Bgr bgr = bgrService.findByUuidAndPassword(bgrId, DigestUtils.sha1Hex(oldPwd));
		if(bgr == null) {
			return new ResBody(0, "原密码输入错误!");
		}
		bgr.setPassword(DigestUtils.sha1Hex(newPwd));
		bgrService.save(bgr);
		return new ResBody(1, "密码修改成功!");
	}
}
