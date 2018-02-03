package top.kylewang.taotao.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.kylewang.taotao.common.pojo.SearchItem;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.search.index.SearchRepository;
import top.kylewang.taotao.search.mapper.ItemMapper;
import top.kylewang.taotao.search.service.SearchItemService;

/**
 * 导入商品数据到索引库
 * @author Kyle.Wang
 * 2018/1/31 0031 22:20
 */

@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SearchRepository searchRepository;
	@Autowired
	private ItemMapper itemMapper;
	@Override
	public TaotaoResult importAllItemToIndex() throws Exception{
		// 1、查询所有商品数据。
		List<SearchItem> itemList = itemMapper.getItemList();
		// 2、添加文档
		searchRepository.save(itemList);
		// 3、返回TaotaoResult。
		return TaotaoResult.ok();
	}

}
