package top.kylewang.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kylewang.taotao.common.pojo.EasyUITreeNode;
import top.kylewang.taotao.service.ItemCatService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/27 0027 11:27
 */
@RestController
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 查询
     * @param parentId
     * @return
     */
    @GetMapping("")
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        return itemCatService.getItemCatList(parentId);
    }
}
