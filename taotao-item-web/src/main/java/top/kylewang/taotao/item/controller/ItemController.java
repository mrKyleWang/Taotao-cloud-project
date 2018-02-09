package top.kylewang.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.kylewang.taotao.feign.ItemFeignClient;
import top.kylewang.taotao.item.pojo.Item;
import top.kylewang.taotao.pojo.TbItem;
import top.kylewang.taotao.pojo.TbItemDesc;

@Controller
public class ItemController {

	@Autowired
	private ItemFeignClient itemFeignClient;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		//根据商品Id查询商品基本信息
		TbItem tbItem = itemFeignClient.getItemById(itemId);
		//使用Tbitem初始化item对象
		Item item = new Item(tbItem);
		//根据商品id查询商品描述
		TbItemDesc tbItemDesc = itemFeignClient.getItemDescById(itemId);
		//传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);
		//返回逻辑视图
		return "item";
	}
	
}
