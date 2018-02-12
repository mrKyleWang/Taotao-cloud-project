package top.kylewang.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.pojo.TbUser;
import top.kylewang.taotao.sso.service.UserRegisterService;

/**
 * @author Kyle.Wang
 * 2018/2/12 0012 17:51
 */
public class UserRegisterCotroller {

    @Autowired
    private UserRegisterService userRegisterService;

    @RequestMapping(value="/user/check/{param}/{type}", method= RequestMethod.POST)
    @ResponseBody
    public TaotaoResult checkUserInfo(@PathVariable String param, @PathVariable Integer type) {
        return userRegisterService.checkUserInfo(param,type);
    }

    @RequestMapping(value="/user/register", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult regitsterUser(TbUser user) {
        return userRegisterService.createUser(user);
    }
}
