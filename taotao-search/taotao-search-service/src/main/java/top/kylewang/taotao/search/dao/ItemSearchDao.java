package top.kylewang.taotao.search.dao;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.kylewang.taotao.common.pojo.SearchItem;
import top.kylewang.taotao.common.pojo.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索dao
 * @author Kyle.Wang
 * 2018/2/6 0006 16:30
 */

@Repository
public class ItemSearchDao {

	@Autowired
	private SolrClient solrClient;
	
	public SearchResult search(SolrQuery query) throws Exception {
		//根据Query对象查询索引库
		QueryResponse response = solrClient.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		SearchResult result = new SearchResult();
		//取查询结果总记录数
		result.setRecordCount(solrDocumentList.getNumFound());
		//取结果集
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem searchItem = new SearchItem();
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setId(Long.parseLong(solrDocument.get("id").toString()));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((Long) solrDocument.get("item_price"));
			//取高亮显示
			String itemTitle = null;
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			
			searchItem.setTitle(itemTitle);
			//添加到商品列表
			itemList.add(searchItem);
		}
		result.setItemList(itemList);
		//返回结果
		return result;
	}
	
}
