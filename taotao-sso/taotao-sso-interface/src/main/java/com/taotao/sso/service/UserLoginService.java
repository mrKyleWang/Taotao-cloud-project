package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

public interface UserLoginService {

	TaotaoResult login(String username, String password);
	TaotaoResult getUserByToken(String token);
}
