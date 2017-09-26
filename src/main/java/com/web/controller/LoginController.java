package com.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utils.ResBody;
import com.web.entity.Bgr;
import com.web.service.BgrService;

@RestController
public class LoginController {
	
	@Autowired
	private BgrService bgrService;
	
	
	@PostMapping("/login")
	public ResBody login(String user, String password, HttpSession session) {
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
			res.setData(bgr);
			session.setAttribute("login_user", bgr);
		}
		return res;
	}
}
