package top.kylewang.taotao.portal.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.kylewang.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/27 0027 13:14
 */
@FeignClient("taotao-manager-service")
public interface ItemCatFeignClient {

    /**
     * 查询列表
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/item/cat/list")
    List<EasyUITreeNode> getItemCatList(@RequestParam("parentId") Long parentId);
}
