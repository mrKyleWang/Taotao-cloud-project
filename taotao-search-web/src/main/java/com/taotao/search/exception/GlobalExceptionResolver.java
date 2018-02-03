package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 * <p>Title: GlobalExceptionResolver</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver{

	private static Logger logger = Logger.getLogger(GlobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			Exception e) {
		//写日志文件
		logger.error("运行时异常", e);
		//发短信、发邮件
		//发短信：第三方运营商服务。发邮件使用jmail包。
		//跳转到错误页面。
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/exception");
		modelAndView.addObject("message", "您的网络异常，请稍后重试。。。。");
		return modelAndView;
	}

}
