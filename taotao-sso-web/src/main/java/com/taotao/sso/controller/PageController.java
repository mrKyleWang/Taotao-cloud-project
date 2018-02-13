package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录注册页面展示Controller
 * <p>Title: PageController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 * 
 */
@Controller
public class PageController {

	@RequestMapping("/page/register")
	public String showRegister() throws Exception {
		return "register";
	}
	@RequestMapping("/page/login")
	public String showLogin(String redirect, Model model) throws Exception {
		model.addAttribute("redirect", redirect);
		return "login";
	}
}
