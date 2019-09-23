package com.itdr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.ServerResponse;
import com.itdr.mapper.CategoryMapper;
import com.itdr.mapper.ProductMapper;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.service.ProductService;
import com.itdr.utils.PoToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public ServerResponse topcategory(Integer pid) {
        if (pid==null||pid<0){
            return ServerResponse.defeatedRS("非法的参数！");
        }
        //根据商品分类ID查询子分类
        List<Category> list=categoryMapper.selectByParentId(pid);
        if (list==null){
            return ServerResponse.defeatedRS("查询的ID不存在！");
        }
        if (list.size()==0){
            return ServerResponse.defeatedRS("没有子分类！");
        }

        return ServerResponse.successRS(list);
    }
    //获取商品详情
    @Override
    public ServerResponse detail(Integer productId, Integer is_new, Integer is_hot, Integer is_banner) {
        if (productId==null||productId<0){
            return ServerResponse.defeatedRS("非法的参数！");
        }
        Product p=productMapper.selectByID(productId,is_new,is_hot,is_banner);

        if (p==null){
            return ServerResponse.defeatedRS("商品不存在！");
        }
        ProductVO productVO =null;
        try {
            productVO=PoToVoUtil.productToProductVO(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.successRS(productVO);
    }
    //产品搜索及排序
    @Override
    public ServerResponse listProduct(Integer productId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        if ((productId==null||productId<0)&&(keyword==null||keyword.equals(""))){
            return ServerResponse.defeatedRS("非法参数！");
        }
        //分割排序参数
        String[] split =new String[2];
        if (!orderBy.equals("")) {
            split = orderBy.split("_");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> list=productMapper.selectByIdOrName(productId,keyword,split[0],split[1]);
        PageInfo pf=new PageInfo(list);
        return ServerResponse.successRS(pf);
    }
}
