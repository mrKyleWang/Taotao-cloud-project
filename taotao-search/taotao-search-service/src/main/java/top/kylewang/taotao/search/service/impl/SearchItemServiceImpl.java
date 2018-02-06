package top.kylewang.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kylewang.taotao.common.pojo.SearchItem;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.search.mapper.ItemMapper;
import top.kylewang.taotao.search.service.SearchItemService;

import java.util.List;

/**
 * 导入商品数据到索引库
 * @author Kyle.Wang
 * 2018/1/31 0031 22:20
 */

@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SolrClient solrClient;
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public TaotaoResult importAllItemToIndex() throws Exception{
		// 1、查询所有商品数据。
		List<SearchItem> itemList = itemMapper.getItemList();
		// 2、创建一个SolrServer对象。
		for (SearchItem searchItem : itemList) {
			// 3、为每个商品创建一个SolrInputDocument对象。
			SolrInputDocument document = new SolrInputDocument();
			// 4、为文档添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_des());
			// 5、向索引库中添加文档。
			solrClient.add(document);
		}
		//提交
		solrClient.commit();
		// 6、返回TaotaoResult。
		return TaotaoResult.ok();
	}

}
