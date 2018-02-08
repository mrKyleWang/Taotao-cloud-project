package top.kylewang.taotao.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.kylewang.taotao.common.pojo.EasyUIDataGridResult;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.pojo.TbItem;
import top.kylewang.taotao.pojo.TbItemDesc;

/**
 * @author Kyle.Wang
 * 2018/1/27 0027 13:14
 */
@FeignClient("taotao-manager-service")
public interface ItemFeignClient {

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    EasyUIDataGridResult getItemList(@RequestParam("page") int page, @RequestParam("rows") int rows);

    /**
     * 添加
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    TaotaoResult addItem(@RequestBody TbItem item, @RequestParam("desc") String desc);

    /**
     * 根据id查询
     * @param itemId
     * @return
     */
    @RequestMapping("/item/getItemById")
    TbItem getItemById(@RequestParam("itemId") Long itemId);


    /**
     * 根据id查询详情
     * @param itemId
     * @return
     */
    @RequestMapping("/item/getItemDescById")
    TbItemDesc getItemDescById(@RequestParam("itemId") Long itemId);
}
