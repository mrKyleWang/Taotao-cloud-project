package top.kylewang.taotao.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.TaotaoResult;


/**
 * @author Kyle.Wang
 * 2018/2/4 0004 19:52
 */
@FeignClient("taotao-search-service")
public interface SearchItemFeignClient {

    /**
     * 导入索引
     * @return
     * @throws Exception
     */
    @RequestMapping("/index/import")
    @ResponseBody
    TaotaoResult indexImport() throws Exception ;


}
