package top.kylewang.taotao.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.kylewang.taotao.common.pojo.EasyUIDataGridResult;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.pojo.TbContent;

/**
 * @author Kyle.Wang
 * 2018/1/27 0027 13:14
 */
@FeignClient("taotao-content-service")
public interface ContentFeignClient {

    /**
     * 新增
     * @param content
     * @return
     */
    @RequestMapping("/content/save")
    TaotaoResult insertContent(@RequestBody TbContent content);

    /**
     * 列表查询
     * @param categoryId
     * @return
     */
    @RequestMapping("/content/query/list")
    EasyUIDataGridResult getContentList(@RequestParam("categoryId") Long categoryId);

    /**
     * 列表查询
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/content/query/pageData")
    EasyUIDataGridResult getPageData(@RequestParam("categoryId") Long categoryId, @RequestParam("page") int page, @RequestParam("rows") int rows);


}
