package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;

public interface UserService {
    ServerResponse login(String username, String password);

    ServerResponse register(User u);

    ServerResponse check(String str, String type);

    ServerResponse findQuestion(String uname);
    //提交问题答案
    ServerResponse checkAnswer(String uname, String question, String answer);

    ServerResponse  updatePassword(String passwordOld, String passwordNew,User u);

    ServerResponse getInformation(User user);

    ServerResponse update_information(User u);

    ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken);

    ServerResponse reset_password(User u, String passwordOld, String passwordNew);
}
