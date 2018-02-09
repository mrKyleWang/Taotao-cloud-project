package top.kylewang.taotao.item.listener;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import top.kylewang.taotao.feign.ItemFeignClient;
import top.kylewang.taotao.item.pojo.Item;
import top.kylewang.taotao.pojo.TbItem;
import top.kylewang.taotao.pojo.TbItemDesc;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 监听商品添加事件，生成商品静态页面
 * @author Kyle.Wang
 * 2018/2/8 0008 21:44
 */

public class HtmlGenListener {

	@Autowired
	private ItemFeignClient itemFeignClient;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;

	@RabbitListener(queues="item-add-topic")
	public void onMessage(String strItemId) {
		try {
			// 2、从message中取商品id
			Long itemId = new Long(strItemId);
			// 3、查询商品基本消息、商品描述。
			TbItem tbItem = itemFeignClient.getItemById(itemId);
			Item item = new Item(tbItem);
			TbItemDesc tbItemDesc = itemFeignClient.getItemDescById(itemId);
			//创建数据集
			Map data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", tbItemDesc);
			// 4、创建商品详情页面的模板。
			// 5、指定文件输出目录
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.htm");
			FileWriter out = new FileWriter(new File(HTML_OUT_PATH + itemId + ".html"));
			// 6、生成静态文件。
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
