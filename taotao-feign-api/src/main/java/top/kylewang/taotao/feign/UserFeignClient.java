package top.kylewang.taotao.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.pojo.TbUser;

/**
 * @author Kyle.Wang
 * 2018/2/12 0012 17:58
 */
@FeignClient("taotao-sso-service")
public interface UserFeignClient {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    TaotaoResult userLogin(String username, String password);

    /**
     * 根据token取用户信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    Object getUserByToken(@PathVariable String token);

    /**
     * 检查用户信息
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/check/{param}/{type}", method = RequestMethod.POST)
    @ResponseBody
    TaotaoResult checkUserInfo(@PathVariable String param, @PathVariable Integer type);

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    TaotaoResult registerUser(TbUser user);
}
