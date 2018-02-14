package top.kylewang.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录注册页面展示Controller
 * @author Kyle.Wang
 * 2018/2/14 0014 19:14
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
