package com.itdr.controller.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import com.itdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService userService;

    //登录
    @RequestMapping("login.do")
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse sr = userService.login(username, password);
        if (sr.isSuccess()) {
            User u = (User) sr.getData();
            session.setAttribute(Const.LOGINUSER, u);
            User u2 = new User();
            u2.setId(u.getId());
            u2.setUsername(u.getUsername());
            u2.setPassword("");
            u2.setEmail(u.getEmail());
            u2.setCreateTime(u.getCreateTime());
            u2.setUpdateTime(u.getUpdateTime());
            sr.setData(u2);
            return sr;
        }

        return sr;
    }
    //用户注册
    @RequestMapping("register.do")
    public ServerResponse<User> register(User u) {
        ServerResponse sr = userService.register(u);
        return sr;
    }
    //检查用户名或邮箱是否有效
    @RequestMapping("check_valid.do")
    public ServerResponse<User> check(String str, String type) {
        ServerResponse sr = userService.check(str, type);
        return sr;
    }
    //获取用户登录信息
    @RequestMapping("get_user_info.do")
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGINUSER);
        if (user == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getDesc());
        }
        return ServerResponse.successRS(user);
    }
    //退出登录
    @RequestMapping("logout.do")
    public ServerResponse<User> logout(HttpSession session) {
        session.removeAttribute(Const.LOGINUSER);
        return ServerResponse.successRS("退出成功");
    }
    //获取当前登录用户详细信息
    @GetMapping("get_information.do")
    public ServerResponse<User> getInformation(HttpSession session) {
        User user=(User)session.getAttribute(Const.LOGINUSER);
        if (user == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getDesc());
        }else{
            ServerResponse sr=userService.getInformation(user);
            return sr;
        }
    }
    //登录状态更新个人信息
    @RequestMapping("update_information.do")
    @ResponseBody
    public ServerResponse<User> update_information(User u, HttpSession session) {
        User user = (User) session.getAttribute(Const.LOGINUSER);
        if (user == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getDesc());
        }
        u.setId(user.getId());
        u.setUsername(user.getUsername());
        ServerResponse sr = userService.update_information(u);
        return sr;
    }
    //忘记密码，获取密码问题
    @RequestMapping("forget_get_question.do")
    @ResponseBody
    public ServerResponse getQuestion(String username) {
        ServerResponse sr = userService.findQuestion(username);
        return sr;
    }
    //提交密码问题答案
    @RequestMapping("forget_check_answer")
    @ResponseBody
    public ServerResponse getAnswer(String username, String question, String answer) {
        ServerResponse sr = userService.checkAnswer(username, question, answer);
        return sr;
    }
    //忘记密码重设密码
    @RequestMapping("forget_reset_password.do")
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        ServerResponse sr = userService.forget_reset_password(username, passwordNew, forgetToken);
        return sr;
    }
    //登录状态重置密码
    @RequestMapping("reset_password.do")
    public ServerResponse reset_password(String passwordOld, String passwordNew,HttpSession session) {
        User u = (User) session.getAttribute(Const.LOGINUSER);
        if (u==null){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getDesc());
        }else {
            ServerResponse sr = userService.reset_password(u, passwordOld, passwordNew);
            return sr;
        }
    }
}
