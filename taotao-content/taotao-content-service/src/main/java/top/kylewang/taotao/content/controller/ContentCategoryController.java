package top.kylewang.taotao.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kylewang.taotao.common.pojo.EasyUITreeNode;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.content.service.ContentCategoryService;

import java.util.List;

/**
 * 内容分类管理api
 * @author Kyle.Wang
 * 2018/1/29 0029 14:56
 */

@RestController
public class ContentCategoryController{

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/content/category/list")
	public List<EasyUITreeNode> getContentCatList(@RequestParam("parentId")Long parentId) {
		return contentCategoryService.getContentCatList(parentId);
	}

	@RequestMapping("/content/category/create")
	public TaotaoResult insertContentCat(@RequestParam("parentId") Long parentId,@RequestParam("name") String name) {
		return contentCategoryService.insertContentCat(parentId,name);
	}

}
