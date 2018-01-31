package top.kylewang.taotao.content.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kylewang.taotao.common.pojo.EasyUIDataGridResult;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.content.service.ContentService;
import top.kylewang.taotao.pojo.TbContent;
import top.kylewang.taotao.pojo.TbContentExample;

import java.util.List;

/**
 * 内容管理api
 * @author Kyle.Wang
 * 2018/1/29 0029 14:58
 */

@RestController
public class ContentController{

	@Autowired
	private ContentService contentService;

	/**
	 * 保存
	 * @param content
	 * @return
	 */
	@RequestMapping("/content/save")
	public TaotaoResult insertContent(@RequestBody TbContent content) {
		return contentService.insertContent(content);
	}


	/**
	 * 列表查询
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/content/query/list")
	List<TbContent> getContentList(@RequestParam("categoryId") Long categoryId){
		return contentService.getContentList(categoryId);
	}

	/**
	 * 分页查询
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/pageData")
	EasyUIDataGridResult getContentList(@RequestParam("categoryId")Long categoryId, @RequestParam("page") int page, @RequestParam("rows")int rows){

		List<TbContent> list = contentService.getContentList(categoryId);

		//分页处理
		PageHelper.startPage(page, rows);
		//执行查询
		TbContentExample example = new TbContentExample();
		//创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取总记录数
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

}
