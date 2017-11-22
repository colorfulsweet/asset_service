package com.web.controller;

import javax.servlet.http.HttpSession;

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
import com.web.service.LzxxService;
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
	
	@Autowired
	private LzxxService lzxxService;
	
	/**
	 * web端首页 - 已登录转到主页面,未登录转到登陆页
	 * @param session
	 * @return
	 */
	@GetMapping({"","/"})
	public String index(HttpSession session) {
		if(session.getAttribute("login_user") == null) {
			return "login";
		} else {
			return "index";
		}
	}
	
	/**
	 * 欢迎页
	 * @return
	 */
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
	
	/**
	 * web端的登陆
	 * @param bgr 
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("/login")
	public String login(Bgr bgr, Model model, HttpSession session) {
		Bgr resultBgr = bgrService.checkUser(bgr.getUser(), 
				//在前端执行加密操作
//				DigestUtils.sha1Hex(
				bgr.getPassword()
//				)
				);
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
	
	/**
	 * 资产列表页面
	 * @param model 视图model
	 * @param page 分页封装对象
	 * @param zcID 资产ID
	 * @param mingch 名称
	 * @param lbie 类别
	 * @return
	 */
	@RequestMapping("/zc/list")
	public String zcList(Model model, PageUtil page, String zcID, String mingch, String lbie) {
		model.addAttribute("zcList", zichanService.find(zcID, mingch, lbie, null, page));
		model.addAttribute("page", page);
		model.addAttribute("zcID", zcID);
		model.addAttribute("mingch", mingch);
		model.addAttribute("lbie", lbie);
		return "zc/list";
	}
	/**
	 * 资产新增
	 * @return
	 */
	@GetMapping("/zc/add")
	public String zcAdd() {
		return "zc/add";
	}
	/**
	 * 资产的修改或查看页面
	 * @param model 视图model
	 * @param uuid 数据主键ID
	 * @param operate 操作-modify编辑, view查看
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
	 * @param session
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
	
	/**
	 * 返回流转历史列表页面
	 * @return
	 */
	@RequestMapping("/lz/history")
	public String lzHistory(Model model, String mingch, PageUtil page) {
		model.addAttribute("lzList", lzxxService.find(mingch, page));
		model.addAttribute("page", page);
		model.addAttribute("mingch", mingch);
		return "lz/history";
	}
}
