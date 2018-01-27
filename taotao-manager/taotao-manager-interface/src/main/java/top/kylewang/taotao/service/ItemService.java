package top.kylewang.taotao.service;

import top.kylewang.taotao.common.pojo.EasyUIDataGridResult;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.pojo.TbItem;
import top.kylewang.taotao.pojo.TbItemDesc;

public interface ItemService {

	EasyUIDataGridResult getItemList(int page, int rows);
	TaotaoResult addItem(TbItem item, String desc);
	TbItem getItemById(long itemId);
	TbItemDesc getItemDescById(long itemId);
}
