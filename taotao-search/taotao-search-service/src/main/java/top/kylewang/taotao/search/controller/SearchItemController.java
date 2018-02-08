package top.kylewang.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.search.service.SearchItemService;

/**
 * @author Kyle.Wang
 * 2018/2/4 0004 20:07
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult indexImport() throws Exception {
        TaotaoResult taotaoResult = searchItemService.importAllItemToIndex();
        return taotaoResult;
    }


}
