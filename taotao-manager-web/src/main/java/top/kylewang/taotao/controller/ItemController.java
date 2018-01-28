package top.kylewang.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kylewang.taotao.common.pojo.EasyUIDataGridResult;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.feign.ItemFeignClient;
import top.kylewang.taotao.pojo.TbItem;

/**
 * 商品管理Controller
 * @author Kyle.Wang
 * 2018/1/27 0027 14:19
 */

@RestController
public class ItemController {

	@Autowired
	private ItemFeignClient itemFeignClient;
	
	@RequestMapping("/item/list")
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemFeignClient.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping("/item/save")
	public TaotaoResult addItem(TbItem item, String desc) {
		TaotaoResult result = itemFeignClient.addItem(item, desc);
		return result;
	}
	
}
