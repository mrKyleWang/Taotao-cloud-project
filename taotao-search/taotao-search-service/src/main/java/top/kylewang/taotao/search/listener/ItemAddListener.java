package top.kylewang.taotao.search.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import top.kylewang.taotao.common.pojo.SearchItem;
import top.kylewang.taotao.search.index.SearchRepository;
import top.kylewang.taotao.search.mapper.ItemMapper;

public class ItemAddListener{

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SearchRepository searchRepository;

	@RabbitListener(queues="item-add-topic")
	public void onMessage(String message) {
		// 从消息中取商品id
		try {
			//转换成Long
			Long itemId = new Long(message);
			//根据商品Id查询商品消息
			SearchItem searchItem = itemMapper.getItemById(itemId);
			//把商品消息添加到索引库
			searchRepository.save(searchItem);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
