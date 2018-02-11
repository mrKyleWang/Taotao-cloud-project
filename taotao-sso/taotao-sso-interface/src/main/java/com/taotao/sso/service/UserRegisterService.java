package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserRegisterService {

	TaotaoResult checkUserInfo(String param, int type);
	TaotaoResult createUser(TbUser user);
}
