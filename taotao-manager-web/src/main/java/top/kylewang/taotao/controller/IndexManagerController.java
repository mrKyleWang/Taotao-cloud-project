package top.kylewang.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.feign.SearchItemFeignClient;

/**
 * 索引库维护controller
 * <p>Title: IndexManagerController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class IndexManagerController {

	@Autowired
	private SearchItemFeignClient searchItemFeignClient;
	
	@RequestMapping("/index/import")
	@ResponseBody
	public TaotaoResult indexImport() throws Exception {
		
		TaotaoResult taotaoResult = searchItemFeignClient.indexImport();
		return taotaoResult;
	}
}
