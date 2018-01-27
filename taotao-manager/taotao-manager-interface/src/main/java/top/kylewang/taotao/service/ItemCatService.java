package top.kylewang.taotao.service;

import top.kylewang.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

	List<EasyUITreeNode> getItemCatList(long parentId);
}
