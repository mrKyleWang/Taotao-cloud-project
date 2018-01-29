package top.kylewang.taotao.content.service;

import top.kylewang.taotao.common.pojo.EasyUITreeNode;
import top.kylewang.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCatList(Long parentId);
	TaotaoResult insertContentCat(Long parentId, String name);
}
