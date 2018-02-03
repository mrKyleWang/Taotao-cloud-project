package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * 商品搜索controller
 * <p>Title: SearchController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	
	@RequestMapping("/search")
	public String searchItem(@RequestParam("q")String queryString, 
			@RequestParam(defaultValue="1")Integer page, Model model) throws Exception {
		queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
		//调用服务搜索商品信息
		SearchResult searchResult = searchService.search(queryString, page, ITEM_ROWS);
		//向页面传递参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		//int a = 1/0;
		//返回逻辑视图
		return "search";
	}
}
