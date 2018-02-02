package top.kylewang.taotao.search.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kylewang.taotao.common.pojo.SearchResult;
import top.kylewang.taotao.search.dao.ItemSearchDao;
import top.kylewang.taotao.search.index.SearchRepository;
import top.kylewang.taotao.search.service.SearchService;

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
    private ItemSearchDao itemSearchDao;

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        // 运单号等值查询
        if (StringUtils.isNotBlank(wayBill.getWayBillNum())) {
            TermQueryBuilder wayBillNumQuery = new TermQueryBuilder("wayBillNum", wayBill.getWayBillNum());
            queryBuilder.must(wayBillNumQuery);
        }
        // 发货地模糊查询
        if (StringUtils.isNotBlank(wayBill.getSendAddress())) {
            // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
            WildcardQueryBuilder sendAddressWildcardQuery = new WildcardQueryBuilder("sendAddress", "*" + wayBill.getSendAddress() + "*");
            // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
            QueryStringQueryBuilder sendAddressQueryStringQuery = new QueryStringQueryBuilder(wayBill.getSendAddress()).field("sendAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
            // 对两种情况下的查询取or 关系(should)
            BoolQueryBuilder sendAddressQuery = new BoolQueryBuilder();
            sendAddressQuery.should(sendAddressWildcardQuery);
            sendAddressQuery.should(sendAddressQueryStringQuery);
            queryBuilder.must(sendAddressQuery);
        }
        // 收货地模糊查询
        if (StringUtils.isNotBlank(wayBill.getRecAddress())) {
            // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
            WildcardQueryBuilder recAddressWildcardQuery = new WildcardQueryBuilder("recAddress", "*" + wayBill.getRecAddress() + "*");
            // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
            QueryStringQueryBuilder recAddressQueryStringQuery = new QueryStringQueryBuilder(wayBill.getRecAddress()).field("recAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
            // 对两种情况下的查询取or 关系(should)
            BoolQueryBuilder recAddressQuery = new BoolQueryBuilder();
            recAddressQuery.should(recAddressWildcardQuery);
            recAddressQuery.should(recAddressQueryStringQuery);
            queryBuilder.must(recAddressQuery);
        }
        // 快递产品类型等值查询
        if (StringUtils.isNotBlank(wayBill.getSendProNum())) {
            TermQueryBuilder sendProNumQuery = new TermQueryBuilder("sendProNum", wayBill.getSendProNum());
            queryBuilder.must(sendProNumQuery);
        }
        // 运单状态等值查询
        if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
            TermQueryBuilder signStatusQuery = new TermQueryBuilder("signStatus", wayBill.getSignStatus());
            queryBuilder.must(signStatusQuery);
        }


        // 1、创建一个SolrQuery对象。
        SolrQuery query = new SolrQuery();
        // 2、设置查询条件
        query.setQuery(queryString);
        // 3、设置分页条件
        if (page < 1) page = 1;
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        // 4、需要指定默认搜索域。
        query.set("df", "item_title");
        // 5、设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        // 6、执行查询，调用SearchDao。得到SearchResult
        SearchResult searchResult = itemSearchDao.search(query);
        // 7、需要计算总页数。
        long recordCount = searchResult.getRecordCount();
        long pageCount = recordCount / rows;
        if (recordCount % rows > 0) {
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        // 返回SearchResult
        return searchResult;
    }

}
