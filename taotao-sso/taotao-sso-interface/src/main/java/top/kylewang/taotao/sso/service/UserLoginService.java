package top.kylewang.taotao.sso.service;

import top.kylewang.taotao.common.pojo.TaotaoResult;

public interface UserLoginService {

	TaotaoResult login(String username, String password);
	TaotaoResult getUserByToken(String token);
}
