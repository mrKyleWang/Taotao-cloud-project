package top.kylewang.taotao.search.service.impl;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import top.kylewang.taotao.common.pojo.SearchItem;
import top.kylewang.taotao.common.pojo.SearchResult;
import top.kylewang.taotao.search.index.SearchRepository;
import top.kylewang.taotao.search.service.SearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索服务实现类
 * <p>Title: SearchServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 *
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {

        if (page < 1) { page = 1; }

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();

        // title
        // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
        WildcardQueryBuilder titleWildcardQuery = new WildcardQueryBuilder("title", "*" + queryString + "*");
        // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
        QueryStringQueryBuilder titleQueryStringQuery = new QueryStringQueryBuilder(queryString).field("title").defaultOperator(QueryStringQueryBuilder.Operator.AND);
        // sell_point
        // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
        WildcardQueryBuilder sellPointWildcardQuery = new WildcardQueryBuilder("sell_point", "*" + queryString + "*");
        // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
        QueryStringQueryBuilder sellPointQueryStringQuery = new QueryStringQueryBuilder(queryString).field("sell_point").defaultOperator(QueryStringQueryBuilder.Operator.AND);
        // title
        // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
        WildcardQueryBuilder descWildcardQuery = new WildcardQueryBuilder("item_des", "*" + queryString + "*");
        // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
        QueryStringQueryBuilder descQueryStringQuery = new QueryStringQueryBuilder(queryString).field("item_des").defaultOperator(QueryStringQueryBuilder.Operator.AND);

        // 对查询取or 关系(should)
        BoolQueryBuilder itemQuery = new BoolQueryBuilder();
        itemQuery.should(titleWildcardQuery);
        itemQuery.should(titleQueryStringQuery);
        itemQuery.should(sellPointWildcardQuery);
        itemQuery.should(sellPointQueryStringQuery);
        itemQuery.should(descWildcardQuery);
        itemQuery.should(descQueryStringQuery);

        queryBuilder.must(itemQuery);

        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");

        SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient()
                .prepareSearch("taotao").setTypes("item").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setHighlighterPreTags("<span style=\"color:red\">").setHighlighterPostTags("</span>").setQuery(queryBuilder)
                .setFrom((page-1)*rows).setSize(rows);

        SearchResponse searchResponse = searchRequestBuilder.get();
        SearchHit[] hits = searchResponse.getHits().getHits();
        //接受结果
        List<SearchItem> itemList = new ArrayList<>();
        //遍历结果
        for(SearchHit hit:searchResponse.getHits()){
            SearchItem searchItem = new SearchItem();
            Map<String, Object> source = hit.getSource();
            searchItem.setId(Long.getLong(source.get("id").toString()));
            searchItem.setTitle(source.get("title").toString());
            searchItem.setSell_point(source.get("sell_point").toString());
            searchItem.setPrice(Long.getLong(source.get("price").toString()));
            searchItem.setImage(source.get("image").toString());
            searchItem.setCategory_name(source.get("category_name").toString());
            searchItem.setItem_des(source.get("item_des").toString());

            //处理高亮片段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField titleField = highlightFields.get("title");
            HighlightField sellPointField = highlightFields.get("sell_point");
            HighlightField itemDesField = highlightFields.get("item_des");
            if(titleField!=null){
                Text[] fragments = titleField.fragments();
                String tmp ="";
                for(Text text:fragments){
                    tmp+=text;
                }
                searchItem.setTitle(tmp);
            }
            if(sellPointField!=null){
                Text[] fragments = sellPointField.fragments();
                String tmp ="";
                for(Text text:fragments){
                    tmp+=text;
                }
                //将高亮片段组装到结果中去
                searchItem.setSell_point(tmp);
            }
            if(itemDesField!=null){
                Text[] fragments = itemDesField.fragments();
                String tmp ="";
                for(Text text:fragments){
                    tmp+=text;
                }
                //将高亮片段组装到结果中去
                searchItem.setItem_des(tmp);
            }
            itemList.add(searchItem);
        }

        SearchResult searchResult = new SearchResult();
        searchResult.setItemList(itemList);
        searchResult.setPageCount((searchResponse.getSuccessfulShards()/rows)+1);
        searchResult.setRecordCount(searchResponse.getSuccessfulShards());
        return searchResult;
    }

}
