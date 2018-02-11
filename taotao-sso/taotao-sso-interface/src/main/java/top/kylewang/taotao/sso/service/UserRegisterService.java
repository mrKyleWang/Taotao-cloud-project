package top.kylewang.taotao.sso.service;

import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.pojo.TbUser;

public interface UserRegisterService {

	TaotaoResult checkUserInfo(String param, int type);
	TaotaoResult createUser(TbUser user);
}
