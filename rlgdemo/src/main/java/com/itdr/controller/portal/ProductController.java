package com.itdr.controller.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;
import com.itdr.pojo.User;
import com.itdr.service.ProductService;
import com.itdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    ProductService productService;

    //获取商品分类
    @RequestMapping("topcategory.do")
    public ServerResponse topcategory(Integer pid) {
        return productService.topcategory(pid);
    }

    //获取商品detail
    @RequestMapping("detail.do")
    public ServerResponse detail(Integer productId,
                                 @RequestParam(value = "is_new",required = false,defaultValue = "0") Integer is_new,
                                 @RequestParam(value = "is_hot",required = false,defaultValue = "0")Integer is_hot,
                                 @RequestParam(value = "is_banner",required = false,defaultValue = "0")Integer is_banner) {
        return productService.detail(productId,is_new,is_hot,is_banner);
    }
    //产品搜索及排序
    @RequestMapping("list.do")
    public ServerResponse listProduct(Integer productId,
                                      String keyword,
                                      @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                      @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                      @RequestParam(value = "orderBy",required = false,defaultValue = "")String orderBy) {
        return productService.listProduct(productId,keyword,pageNum,pageSize,orderBy);
    }


}
