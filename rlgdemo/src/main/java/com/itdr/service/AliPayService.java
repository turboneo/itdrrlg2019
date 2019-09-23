package com.itdr.service;

import com.itdr.common.ServerResponse;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AliPayService {
    /*订单支付*/
    ServerResponse alipay(Long orderno, Integer id);
    /*回调成功后做的处理*/
    ServerResponse alipayCallback(Map<String, String> map);
    /*查询订单支付状态*/
    ServerResponse selectByOrderNo(Long orderno);
}
