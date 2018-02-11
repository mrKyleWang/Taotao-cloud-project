package top.kylewang.taotao.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.common.utils.JsonUtils;
import top.kylewang.taotao.jedis.JedisClient;
import top.kylewang.taotao.mapper.TbUserMapper;
import top.kylewang.taotao.pojo.TbUser;
import top.kylewang.taotao.pojo.TbUserExample;
import top.kylewang.taotao.pojo.TbUserExample.Criteria;
import top.kylewang.taotao.sso.service.UserLoginService;

/**
 * 用户登录处理Service
 * <p>Title: UserLoginServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${SESSION_PRE}")
	private String SESSION_PRE;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public TaotaoResult login(String username, String password) {
		//判断用户名密码是否正确
		TbUserExample example = new TbUserExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		//校验密码
		TbUser user = list.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		//生成token
		String token = UUID.randomUUID().toString();
		//把用户信息保存到redis中.key就是token，value就是用户对象转换成json
		user.setPassword(null);
		jedisClient.set(SESSION_PRE + ":"+ token, JsonUtils.objectToJson(user));
		//设置key的过期时间
		jedisClient.expire(SESSION_PRE + ":"+ token, SESSION_EXPIRE);
		//返回结果
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		// 根据token到redis中查询用户信息
		String json = jedisClient.get(SESSION_PRE + ":"+ token);
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "此用户登录已经过期");
		}
		//重置过期时间
		jedisClient.expire(SESSION_PRE + ":"+ token, SESSION_EXPIRE);
		//转换成用户对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		//返回结果
		return TaotaoResult.ok(user);
	}

}
