package top.kylewang.taotao.search.index;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.kylewang.taotao.common.pojo.SearchItem;

/**
 * @author Kyle.Wang
 * 2018/1/31 0031 21:54
 */
public interface SearchRepository extends ElasticsearchRepository<SearchItem,Long>{
}
