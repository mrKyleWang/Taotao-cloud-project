package top.kylewang.taotao.content.impl;

import top.kylewang.taotao.common.utils.JsonUtils;
import top.kylewang.taotao.pojo.TbContent;
import top.kylewang.taotao.pojo.TbContentExample;
import top.kylewang.taotao.pojo.TbContentExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.content.jedis.JedisClient;
import top.kylewang.taotao.content.service.ContentService;
import top.kylewang.taotao.mapper.TbContentMapper;

import java.util.Date;
import java.util.List;

/**
 * 内容管理Service
 * @author Kyle.Wang
 * 2018/1/29 0029 14:58
 */

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全pojo的属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//向内容表插入数据
		contentMapper.insert(content);
		
		//缓存同步，清除redis中cid对应的缓存信息
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());
		
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentList(Long cid) {
		//先查询缓存
		try {
			String json = jedisClient.hget(CONTENT_KEY, cid + "");
			//判断是否命中缓存
			if (StringUtils.isNotBlank(json)) {
				//把json转换成list
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据分类id查询内容列表
		TbContentExample example = new TbContentExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//向缓存中保存结果
		try {
			jedisClient.hset(CONTENT_KEY, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
