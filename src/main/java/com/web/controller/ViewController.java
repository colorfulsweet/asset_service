package com.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utils.PageUtil;
import com.utils.ResBody;
import com.web.entity.Bgr;
import com.web.entity.Zichan;
import com.web.service.BgrService;
import com.web.service.ZichanService;

/**
 * 页面视图相关控制器
 * @author 夏夜梦星辰
 *
 */
@Controller
@RequestMapping("/views")
public class ViewController {
	
	@Autowired
	private BgrService bgrService;
	
	@Autowired
	private ZichanService zichanService;
	
	@GetMapping({"","/"})
	public String index(HttpSession session) {
		if(session.getAttribute("login_user") == null) {
			return "login";
		} else {
			return "index";
		}
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
	
	@PostMapping("/login")
	public String login(Bgr bgr, Model model, HttpSession session) {
		Bgr resultBgr = bgrService.checkUser(bgr.getUser(), DigestUtils.sha1Hex(bgr.getPassword()));
		if(resultBgr == null) {
			model.addAttribute("username", bgr.getUser());
			model.addAttribute("msg", "用户名/密码错误");
			return "login";
		}
		session.setAttribute("login_user", resultBgr);
		return "redirect:/views/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/views/";
	}
	
	@GetMapping("/zc/list")
	public String zcList(Model model, PageUtil page) {
		model.addAttribute("zcList", zichanService.findByPage(page));
		return "zc/list";
	}
	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@GetMapping("/zc/add")
	public String zcAdd() {
		return "zc/add";
	}
	/**
	 * 修改
	 * @param model
	 * @return
	 */
	@GetMapping("/zc/get")
	public String zcGet(Model model, String uuid, String operate) {
		model.addAttribute("zc", zichanService.findOne(uuid));
		switch(operate) {
			case "modify" :
				return "zc/modify";
			case "view" : 
				return "zc/view";
			default : return "/error";
		}
	}
	
	/**
	 * 新增或修改的保存
	 * @param zichan 资产数据
	 * @return
	 */
	@PostMapping("/zc/save")
	@ResponseBody
	public ResBody save(Zichan zichan, HttpSession session) {
		if(zichan.getBgr() == null) {
			Bgr bgr = (Bgr) session.getAttribute("login_user");
			zichan.setBgr(bgr);
		}
		zichanService.save(zichan);
		return new ResBody(1,"保存成功");
	}
}
