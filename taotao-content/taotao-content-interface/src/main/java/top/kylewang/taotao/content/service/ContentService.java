package top.kylewang.taotao.content.service;

import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {

	TaotaoResult insertContent(TbContent content);
	List<TbContent> getContentList(long cid);
}
