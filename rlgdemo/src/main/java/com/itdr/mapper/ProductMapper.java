package com.itdr.mapper;

import com.itdr.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    //根据商品ID获取商品详情
    Product selectByID(@Param("productId") Integer productId,
                       @Param("is_new") Integer is_new,
                       @Param("is_hot") Integer is_hot,
                       @Param("is_banner") Integer is_banner);

    List<Product> selectByIdOrName(@Param("productId")Integer productId,
                                   @Param("keyword")String keyword,
                                   @Param("col")String col,
                                   @Param("order")String order);
}