package top.kylewang.taotao.search.listener;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import top.kylewang.taotao.common.pojo.SearchItem;
import top.kylewang.taotao.search.mapper.ItemMapper;

/**
 * 商品添加监听器
 * @author Kyle.Wang
 * 2018/2/6 0006 16:39
 */

public class ItemAddListener{

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private SolrClient solrClient;

	@RabbitListener(queues="item-add-topic")
	public void onMessage(String strItemId) {
		try {
			//从消息中取商品id转换成Long
			Long itemId = new Long(strItemId);
			//根据商品Id查询商品消息
			SearchItem searchItem = itemMapper.getItemById(itemId);
			//把商品消息添加到索引库
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
			//提交
			solrClient.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
