package top.kylewang.taotao.content.impl;

import top.kylewang.taotao.common.pojo.EasyUITreeNode;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.content.service.ContentCategoryService;
import top.kylewang.taotao.mapper.TbContentCategoryMapper;
import top.kylewang.taotao.pojo.TbContentCategory;
import top.kylewang.taotao.pojo.TbContentCategoryExample;
import top.kylewang.taotao.pojo.TbContentCategoryExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理服务
 * @author Kyle.Wang
 * 2018/1/29 0029 14:56
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) {
		//根据parentid查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//返回结果List
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到列表
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCat(Long parentId, String name) {
		//创建一个内容分类对象
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		//新添加的节点都是叶子节点
		contentCategory.setIsParent(false);
		//排序方法默认为1
		contentCategory.setSortOrder(1);
		//1(正常),2(删除)
		contentCategory.setStatus(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入节点
		contentCategoryMapper.insert(contentCategory);
		//判断父节点是否为叶子节点
		TbContentCategory parnetNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parnetNode.getIsParent()) {
			parnetNode.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parnetNode);
		}
		
		return TaotaoResult.ok(contentCategory);
	}

}
