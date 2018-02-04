package top.kylewang.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.kylewang.taotao.common.pojo.SearchResult;
import top.kylewang.taotao.search.service.SearchService;

/**
 * @author Kyle.Wang
 * 2018/2/4 0004 20:07
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public SearchResult search(@RequestParam("queryString") String queryString, @RequestParam("page")int page, @RequestParam("rows")int rows) throws Exception {
        return searchService.search(queryString,page,rows);
    }

}
