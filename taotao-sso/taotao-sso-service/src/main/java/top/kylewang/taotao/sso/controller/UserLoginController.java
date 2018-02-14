package top.kylewang.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.sso.service.UserLoginService;

/**
 * @author Kyle.Wang
 * 2018/2/12 0012 17:51
 */
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;


    @RequestMapping(value="/user/login", method= RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(String username, String password) {
        return userLoginService.login(username,password);
    }

    @RequestMapping(value="/user/token/{token}")
    @ResponseBody
    public TaotaoResult getUserByToken(@PathVariable String token) {
       return userLoginService.getUserByToken(token);
    }



}
