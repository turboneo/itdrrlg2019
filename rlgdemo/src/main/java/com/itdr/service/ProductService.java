package com.itdr.service;

import com.itdr.common.ServerResponse;

public interface ProductService {
    //获取商品分类信息
    ServerResponse topcategory(Integer pid);

    ServerResponse detail(Integer productId, Integer is_new, Integer is_hot, Integer is_banner);
//商品搜索和动态排序
    ServerResponse listProduct(Integer productId, String keyword, Integer pageNum, Integer pageSize, String orderBy);
}
