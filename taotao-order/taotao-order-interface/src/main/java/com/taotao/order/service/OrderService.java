package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.service.pojo.OrderInfo;

public interface OrderService {

	TaotaoResult createOrder(OrderInfo orderInfo);
}
