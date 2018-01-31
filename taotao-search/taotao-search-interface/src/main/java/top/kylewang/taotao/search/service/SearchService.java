package top.kylewang.taotao.search.service;

import top.kylewang.taotao.common.pojo.SearchResult;

/**
 * 商品搜索服务
 * <p>Title: SearchService</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
public interface SearchService {
	SearchResult search(String queryString, int page, int rows)  throws Exception;
}
