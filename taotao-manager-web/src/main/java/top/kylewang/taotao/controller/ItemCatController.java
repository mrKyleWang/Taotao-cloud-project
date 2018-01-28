package top.kylewang.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.EasyUITreeNode;
import top.kylewang.taotao.feign.ItemCatFeignClient;

import java.util.List;

/**
 * 商品分类管理Controller
 * @author Kyle.Wang
 * 2018/1/27 0027 14:10
 */

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatFeignClient itemCatFeignClient;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> list = itemCatFeignClient.getItemCatList(parentId);
		return list;
	}
}
