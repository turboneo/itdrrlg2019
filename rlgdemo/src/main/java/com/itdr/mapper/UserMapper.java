package com.itdr.mapper;

import com.itdr.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //根据用户名和密码查询用户
    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User selectByUsername(String username);

    User selectByEmail(String email);

    int selectByUsernameOrEmail(@Param("str") String str, @Param("type") String type);
    int selectByEmailAndId(@Param("email")String email,  @Param("id")Integer id);

    int updateByUsernameAndPassword(@Param("username") String username, @Param("password") String passwordNew);

    //根据用户id查询密码是否正确
    int selectByIdAndPassword(@Param("id") Integer id, @Param("passwordOld") String passwordOld);
}