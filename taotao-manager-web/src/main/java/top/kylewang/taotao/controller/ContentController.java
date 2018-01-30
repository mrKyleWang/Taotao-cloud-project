package top.kylewang.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.EasyUIDataGridResult;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.feign.ContentFeignClient;
import top.kylewang.taotao.pojo.TbContent;

/**
 * 内容管理Controller
 * @author Kyle.Wang
 * 2018/1/29 0029 15:35
 */

@Controller
public class ContentController {

	@Autowired
	private ContentFeignClient contentFeignClient;
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content) {
		TaotaoResult result = contentFeignClient.insertContent(content);
		return result;
	}

	@RequestMapping("/content/query/pageData")
	@ResponseBody
	EasyUIDataGridResult getPageData(Long categoryId, int page, int rows){
		EasyUIDataGridResult result = contentFeignClient.getPageData(categoryId,page,rows);
		return result;
	}
	
}
