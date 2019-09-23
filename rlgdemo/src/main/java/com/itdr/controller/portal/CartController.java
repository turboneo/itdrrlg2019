package com.itdr.controller.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.CartVO;
import com.itdr.service.CartService;
import com.itdr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    CartService cartService;
    //购物车添加商品
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse addOne(Integer productId, Integer count, HttpSession session){
        User user = (User) session.getAttribute(Const.LOGINUSER);
        if (user==null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),Const.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.addOne(productId,count,user.getId());
    }

    //购物车List列表
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse listCart(HttpSession session){
        User user = (User) session.getAttribute(Const.LOGINUSER);
        if (user==null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),Const.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.listCart(user.getId());
    }

    //更新购物车某个产品数量
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse updateCart(Integer productId,Integer count,HttpSession session){
        User user = (User) session.getAttribute(Const.LOGINUSER);
        if (user==null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),Const.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.updateCart(productId,count,user.getId());
    }

    //移除购物车某个产品
    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse deleteCart(String productIds,HttpSession session){
        User user = (User) session.getAttribute(Const.LOGINUSER);
        if (user==null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),Const.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.deleteCart(productIds,user.getId());
    }

    //查询在购物车里的商品信息条数
    @PostMapping("get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        User users = (User) session.getAttribute(Const.LOGINUSER);
        if(users == null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),
                    Const.UserEnum.NO_LOGIN.getDesc());
        }else{
            return cartService.getCartProductCount(users.getId());
        }
    }


    //购物车全选
    @PostMapping("select_all.do")
    public ServerResponse<CartVO> selectAll(HttpSession session, Integer check) {
        User users = (User) session.getAttribute(Const.LOGINUSER);
        if(users == null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),
                    Const.UserEnum.NO_LOGIN.getDesc());
        }else{
            return cartService.selectOrUnSelect(users.getId(),check,null);
        }
    }

    //购物车取消全选
    @PostMapping("un_select_all.do")
    public ServerResponse<CartVO> unSelectAll(HttpSession session,Integer check) {
        User users = (User) session.getAttribute(Const.LOGINUSER);
        if(users == null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),
                    Const.UserEnum.NO_LOGIN.getDesc());
        }else{
            return cartService.selectOrUnSelect(users.getId(),check,null);
        }
    }

    //购物车选中某个商品
    @PostMapping("select.do")
    public ServerResponse<CartVO> select(HttpSession session,Integer check,Integer productId) {
        User users = (User) session.getAttribute(Const.LOGINUSER);
        if(users == null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),
                    Const.UserEnum.NO_LOGIN.getDesc());
        }else{
            return cartService.selectOrUnSelect(users.getId(),check,productId);
        }
    }


    //购物车取消选中某个商品
    @PostMapping("un_select.do")
    public ServerResponse<CartVO> unSelect(HttpSession session,Integer check,Integer productId) {
        User users = (User) session.getAttribute(Const.LOGINUSER);
        if(users == null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(),
                    Const.UserEnum.NO_LOGIN.getDesc());
        }else{
            return cartService.selectOrUnSelect(users.getId(),check,productId);
        }
    }

}
