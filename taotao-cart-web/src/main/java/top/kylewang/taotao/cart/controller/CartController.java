package top.kylewang.taotao.cart.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.taotao.common.pojo.TaotaoResult;
import top.kylewang.taotao.common.utils.CookieUtils;
import top.kylewang.taotao.common.utils.JsonUtils;
import top.kylewang.taotao.feign.ItemFeignClient;
import top.kylewang.taotao.pojo.TbItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车管理Controller
 * @author Kyle.Wang
 * 2018/1/31 0031 21:30
 */

@Controller
public class CartController {
	
	@Autowired
	private ItemFeignClient itemFeignClient;
	
	@Value("${COOKIE_CART_KEY}")
	private String COOKIE_CART_KEY;
	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		//从cookie中查询购物车列表
		List<TbItem> cartList = getCartList(request);
		//判断列表中是否有此商品
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemId.longValue()) {
				//列表中存在此商品，数量相加
				//如果有商品数量相加
				tbItem.setNum(tbItem.getNum() + num);
				flag = true;
				break;
			}
		}
		if (!flag) {
			//如果没有，根据商品Id查询商品消息。调用商品服务实现。
			TbItem tbItem = itemFeignClient.getItemById(itemId);
			//设置商品数量
			tbItem.setNum(num);
			//取一张图片
			String image = tbItem.getImage();
			if (StringUtils.isNotBlank(image)) {
				tbItem.setImage(image.split(",")[0]);
			}
			//添加到商品列表
			cartList.add(tbItem);
		}
		//把购物车写入cookie
		CookieUtils.setCookie(request, response, COOKIE_CART_KEY, 
				JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回添加成功页面。
		return "cartSuccess";
	}

	/**
	 * 	从cookie中取购物车列表
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartList(HttpServletRequest request) {
		//使用CookieUtils取购物车列表
		String json = CookieUtils.getCookieValue(request, COOKIE_CART_KEY, true);
		//判断cookie中是否有值
		if (StringUtils.isBlank(json)) {
			//没有值返回一个空List
			return new ArrayList<>();
		} 
		//把json转换成list对象
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
		
	}
	
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request) {
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartList(request);
		//把购物车列表传递给页面
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "cart";
	}
	
	/**
	 * 更新购物车商品数量
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateNum(@PathVariable Long itemId, @PathVariable Integer num,
								  HttpServletRequest request, HttpServletResponse response) {
		//取购物车商品列表
		List<TbItem> cartList = getCartList(request);
		//找到商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().intValue() == itemId) {
				//更新商品数量
				tbItem.setNum(num);
				break;
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, COOKIE_CART_KEY, 
				JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回结果
		return TaotaoResult.ok();
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
			HttpServletRequest request ,HttpServletResponse response) {
		//取购物车商品列表
		List<TbItem> cartList = getCartList(request);
		//找到对应的商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				//删除商品
				cartList.remove(tbItem);
				//退出循环
				break;
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, COOKIE_CART_KEY, 
				JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回逻辑视图，需要做redirect跳转。
		return "redirect:/cart/cart.html";
	}
}
