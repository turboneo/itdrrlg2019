package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.mapper.UserMapper;
import com.itdr.pojo.User;
import com.itdr.service.UserService;
import com.itdr.utils.GetToken;
import com.itdr.utils.MD5Utils;
import com.itdr.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    //用户登录
    @Override
    public ServerResponse login(String username, String password) {
        //判断用户是否输入了字符
        if (username == "" || password == "") {
            return ServerResponse.defeatedRS("用户名或密码不能为空！");
        }
        //
        String md5Code = MD5Utils.getMD5Code(password);
        //判断用户输入的用户名或密码是否正确
        User u = userMapper.selectByUsernameAndPassword(username, md5Code);
        if (u == null) {
            return ServerResponse.defeatedRS("账户或密码错误！");
        }
        //成功的话
        return ServerResponse.successRS(u);
    }
    //用户注册
    @Override
    public ServerResponse register(User u) {

        if (u.getUsername() == null || u.getUsername() == "") {
            return ServerResponse.defeatedRS(100, "账户名不能为空！");
        }
        if (u.getPassword() == null || u.getPassword() == "") {
            return ServerResponse.defeatedRS(100, "密码不能为空！");
        }

        //检查用户名是否存在
        User user = userMapper.selectByUsername(u.getUsername());
        if (user != null) {
            return ServerResponse.defeatedRS(1, "用户已存在！");
        }
        //检查邮箱是否存在
        User user1 = userMapper.selectByEmail(u.getEmail());
        if (user1 != null) {
            return ServerResponse.defeatedRS(2, "邮箱已注册！");
        }
        //MD5加密
        u.setPassword(MD5Utils.getMD5Code(u.getPassword()));
        int i = userMapper.insert(u);
        if (i <= 0) {
            return ServerResponse.defeatedRS(101, "用户注册失败！");
        }
        return ServerResponse.successRS( "用户注册成功！");
    }
    //检查用户名或者邮箱是否有效
    @Override
    public ServerResponse check(String str, String type) {
        if (str == null || str.equals("")) {
            return ServerResponse.defeatedRS(100, "参数不能为空！");
        }
        if (type == null || type.equals("")) {
            return ServerResponse.defeatedRS(100, "参数类型不能为空！");
        }

        int i = userMapper.selectByUsernameOrEmail(str, type);
        if (i > 0 && type.equals("username")) {
            return ServerResponse.defeatedRS("用户名已经存在！");
        }
        if (i > 0 && type.equals("email")) {
            return ServerResponse.defeatedRS("邮箱已经存在！");
        }
        return ServerResponse.successRS( "校验成功！");
    }
    //忘记密码
    @Override
    public ServerResponse findQuestion(String uname) {
        if (uname == null) {
            return ServerResponse.defeatedRS(100, "用户名不能为空！");
        }
        User user = userMapper.selectByUsername(uname);
        if (user == null) {
            return ServerResponse.defeatedRS(101, "用户名不存在");
        }
        if (user.getQuestion() == null) {
            return ServerResponse.defeatedRS(1, "该用户并未设置找回密码问题！");
        }
        String q = user.getQuestion();
        return ServerResponse.successRS( q, "返回一个问题");
    }
    //提交问题答案
    @Override
    public ServerResponse checkAnswer(String uname, String question, String answer) {
        //非空判断
        if (uname == null||uname.equals("")) {
            return ServerResponse.defeatedRS(100, "用户名不能为空！");
        }
        if (question == null||question.equals("")) {
            return ServerResponse.defeatedRS(100, "问题不能为空！");
        }
        if (answer == null||answer.equals("")) {
            return ServerResponse.defeatedRS(100, "答案不能为空！");
        }

        User user = userMapper.selectByUsername(uname);

        if (user.getAnswer().equals(answer)) {
            String s = GetToken.makeToken();
            //把令牌放入缓存中，这里使用的是Google的guava缓存，后期用redis替代
            TokenCache.set("token_"+uname,s);
            return ServerResponse.successRS(s, "密码问题校验成功");
        } else {
            return ServerResponse.defeatedRS(1, "问题答案错误！");
        }
    }
    //更新密码
    @Override
    public ServerResponse updatePassword(String passwordOld, String passwordNew, User u) {
        return null;
    }
    //获取用户详细信息
    @Override
    public ServerResponse getInformation(User user) {
        User user1 = userMapper.selectByPrimaryKey(user.getId());
        if (user1==null){
            return ServerResponse.defeatedRS("用户不存在");
        }
        user1.setPassword("");
        return ServerResponse.successRS(user1);
    }
    //更新邮箱
    @Override
    public ServerResponse update_information(User u) {
        int i = userMapper.selectByEmailAndId(u.getEmail(), u.getId());
        if (i > 0) {
            return ServerResponse.defeatedRS("用户邮箱已存在");
        }
        int i1 = userMapper.updateByPrimaryKeySelective(u);
        if (i1 <= 0) {
            return ServerResponse.defeatedRS("更新失败");
        }
        return ServerResponse.successRS("更新个人信息成功");
    }

    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        if (username == null || "".equals(username)) {
            return ServerResponse.defeatedRS("用户名不能为空");
        }
        if (passwordNew == null || "".equals(passwordNew)) {
            return ServerResponse.defeatedRS("问题不能为空");
        }
        if (forgetToken == null || "".equals(forgetToken)) {
            return ServerResponse.defeatedRS("Token已失效");
        }

        //判断缓存中的Token

        String token = TokenCache.get("token_" + username);
        if (token == null || "".equals(token)) {
            return ServerResponse.defeatedRS("Token过期了");
        }
        if (!token.equals(forgetToken)) {
            return ServerResponse.defeatedRS("非法的Token");
        }
        String md5Code=MD5Utils.getMD5Code(passwordNew);
        int i = userMapper.updateByUsernameAndPassword(username, md5Code);
        if (i <= 0) {
            return ServerResponse.defeatedRS("修改密码失败");
        }
        return ServerResponse.successRS("修改密码成功");
    }

    @Override
    public ServerResponse reset_password(User u, String passwordOld, String passwordNew) {
        if (passwordOld == null || "".equals(passwordOld)) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        if (passwordNew == null || "".equals(passwordNew)) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        String md5Codeold=MD5Utils.getMD5Code(passwordOld);
        int i = userMapper.selectByIdAndPassword(u.getId(), md5Codeold);
        if (i<=0){
            return ServerResponse.defeatedRS("旧密码输入错误");
        }
        String md5Codenew=MD5Utils.getMD5Code(passwordNew);
        int i1 = userMapper.updateByUsernameAndPassword(u.getUsername(), md5Codenew);
        if (i1<=0){
            return ServerResponse.defeatedRS("密码更新失败");
        }
        return ServerResponse.successRS("密码更新成功");
    }


}
