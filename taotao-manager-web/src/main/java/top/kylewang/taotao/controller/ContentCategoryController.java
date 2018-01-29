package top.kylewang.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.EasyUITreeNode;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.feign.ContentCategoryFeignClient;

import java.util.List;

/**
 * 内容分类管理Controller
 * @author Kyle.Wang
 * 2018/1/29 0029 15:35
 */

@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryFeignClient contentCategoryFeignClient;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(defaultValue="0")Long id) {
		List<EasyUITreeNode> catList = contentCategoryFeignClient.getContentCatList(id);
		return catList;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult insertContentCat(@RequestParam(defaultValue="0")Long parentId, String name) {
		TaotaoResult result = contentCategoryFeignClient.insertContentCat(parentId, name);
		return result;
	}
	
}
