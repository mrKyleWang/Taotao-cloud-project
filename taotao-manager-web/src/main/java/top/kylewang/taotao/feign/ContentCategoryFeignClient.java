package top.kylewang.taotao.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.kylewang.taotao.common.pojo.EasyUITreeNode;
import top.kylewang.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/27 0027 13:14
 */
@FeignClient("taotao-content-service")
public interface ContentCategoryFeignClient {

    /**
     * 查询列表
     * @param parentId
     * @return
     */
    @RequestMapping("/content/category/list")
    List<EasyUITreeNode> getContentCatList(@RequestParam("parentId")Long parentId);

    /**
     * 新增
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/content/category/create")
    TaotaoResult insertContentCat(@RequestParam("parentId") Long parentId,@RequestParam("name") String name);
}
