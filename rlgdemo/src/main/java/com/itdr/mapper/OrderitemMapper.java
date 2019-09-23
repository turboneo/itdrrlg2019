package com.itdr.mapper;



import com.itdr.pojo.Orderitem;

import java.util.List;

public interface OrderitemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orderitem record);

    int insertSelective(Orderitem record);

    Orderitem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orderitem record);

    int updateByPrimaryKey(Orderitem record);

    //根据订单号查对应商品详情
    List<Orderitem> selectByOrderNo(Long oid);
}