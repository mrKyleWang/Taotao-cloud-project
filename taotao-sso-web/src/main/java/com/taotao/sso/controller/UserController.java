package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;
import com.taotao.sso.service.UserRegisterService;

/**
 * 用户管理Controller
 * <p>Title: UserController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class UserController {

	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	private UserLoginService userLoginService;
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@RequestMapping(value="/user/check/{param}/{type}", method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult checkUserInfo(@PathVariable String param, @PathVariable Integer type) {
		TaotaoResult result = userRegisterService.checkUserInfo(param, type);
		return result;
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult regitsterUser(TbUser user) {
		TaotaoResult taotaoResult = userRegisterService.createUser(user);
		return taotaoResult;
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult userLogin(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult taotaoResult = userLoginService.login(username, password);
		//取token
		String token = taotaoResult.getData().toString();
		//设置cookie
		//cookie要跨域、cookie的有效期
		CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
		//返回结果
		return taotaoResult;
	}
	
	//传统支持jsonp的方案
	/*@RequestMapping(value="/user/token/{token}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = userLoginService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			//客户端为jsonp请求。需要返回js代码
			String jsonResutl = callback + "(" + JsonUtils.objectToJson(result) + ");";  
			return jsonResutl;
		}
		return JsonUtils.objectToJson(result);
	}*/
	//方法二，从4.1以后版本才可以使用。
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = userLoginService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			//设置要包装的数据
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			//设置回调方法
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}
}
