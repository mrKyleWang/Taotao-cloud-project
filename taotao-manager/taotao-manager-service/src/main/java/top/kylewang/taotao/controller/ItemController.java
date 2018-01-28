package top.kylewang.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kylewang.taotao.common.pojo.EasyUIDataGridResult;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.pojo.TbItem;
import top.kylewang.taotao.pojo.TbItemDesc;
import top.kylewang.taotao.service.ItemService;

/**
 * @author Kyle.Wang
 * 2018/1/27 0027 11:27
 */
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;


    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    public EasyUIDataGridResult getItemList(int page, int rows) {
        return itemService.getItemList(page,rows);
    }

    /**
     * 添加
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping("/item/save")
    public TaotaoResult addItem(@RequestBody TbItem item, @RequestParam("desc") String desc){
        return itemService.addItem(item, desc);
    }

    /**
     * 根据id查询
     * @param itemId
     * @return
     */
    @RequestMapping("/item/getItemById")
    public TbItem getItemById(long itemId) {
        return itemService.getItemById(itemId);
    }

    /**
     * 根据id查询详情
     * @param itemId
     * @return
     */
    @RequestMapping("/item/getItemDescById")
    public TbItemDesc getItemDescById(long itemId) {
        return itemService.getItemDescById(itemId);
    }
}
