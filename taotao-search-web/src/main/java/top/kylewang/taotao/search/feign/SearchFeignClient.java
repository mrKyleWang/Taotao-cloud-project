package top.kylewang.taotao.search.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.kylewang.taotao.common.pojo.SearchResult;


/**
 * @author Kyle.Wang
 * 2018/2/4 0004 19:52
 */
@FeignClient("taotao-search-service")
public interface SearchFeignClient {

    /**
     * 全文检索
     * @param queryString
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    SearchResult search(@RequestParam("queryString") String queryString, @RequestParam("page")int page, @RequestParam("rows")int rows);

}
