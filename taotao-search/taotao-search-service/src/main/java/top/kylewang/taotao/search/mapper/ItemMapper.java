package top.kylewang.taotao.search.mapper;

import top.kylewang.taotao.common.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);
}
